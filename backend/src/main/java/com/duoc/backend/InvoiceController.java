package com.duoc.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private static final BigDecimal DEFAULT_VAT_RATE = new BigDecimal("0.19");

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping("/appointments/{appointmentId}")
    public ResponseEntity<?> createInvoiceForAppointment(@PathVariable Integer appointmentId, @RequestBody Invoice invoice) {
        try {
            if (!appointmentRepository.existsById(appointmentId)) {
                return ResponseEntity.badRequest().body("Appointment not found for appointmentId: " + appointmentId);
            }

            List<Invoice> existingInvoices = invoiceRepository.findByAppointmentId(appointmentId);
            if (!existingInvoices.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("An invoice already exists for appointmentId: " + appointmentId);
            }

            String validationError = validateInvoicePayload(invoice);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }

            invoice.setAppointmentId(appointmentId);
            if (invoice.getIssueDate() == null) {
                invoice.setIssueDate(LocalDate.now());
            }
            if (invoice.getVatRate() == null) {
                invoice.setVatRate(DEFAULT_VAT_RATE);
            }

            for (InvoiceLineItem item : invoice.getItems()) {
                item.setInvoice(invoice);
            }
            invoice.calculateTotals();

            Invoice savedInvoice = invoiceRepository.save(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInvoice);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al generar factura: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Invoice>> getAllInvoices() {
        try {
            Iterable<Invoice> invoices = invoiceRepository.findAll();
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Integer id) {
        try {
            Optional<Invoice> invoice = invoiceRepository.findById(id);
            return invoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> getInvoiceByAppointment(@PathVariable Integer appointmentId) {
        try {
            if (!appointmentRepository.existsById(appointmentId)) {
                return ResponseEntity.notFound().build();
            }

            List<Invoice> invoices = invoiceRepository.findByAppointmentId(appointmentId);
            if (invoices.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(invoices.get(0));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private String validateInvoicePayload(Invoice invoice) {
        if (invoice == null) {
            return "Invoice payload is required";
        }

        if (invoice.getItems() == null || invoice.getItems().isEmpty()) {
            return "Invoice must include at least one detail item";
        }

        if (invoice.getVatRate() != null && invoice.getVatRate().compareTo(BigDecimal.ZERO) < 0) {
            return "vatRate cannot be negative";
        }

        for (int i = 0; i < invoice.getItems().size(); i++) {
            InvoiceLineItem item = invoice.getItems().get(i);
            int lineNumber = i + 1;

            if (item.getType() == null) {
                return "Item " + lineNumber + " must define type (SERVICE, MEDICATION, ADDITIONAL_CHARGE)";
            }

            if (item.getDescription() == null || item.getDescription().trim().isEmpty()) {
                return "Item " + lineNumber + " must include a description";
            }

            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                return "Item " + lineNumber + " must include a quantity greater than zero";
            }

            if (item.getUnitPrice() == null || item.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
                return "Item " + lineNumber + " must include a unitPrice greater or equal to zero";
            }
        }

        return null;
    }
}
