package com.duoc.seguridadcalidad;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

    private final BackendService backendService;
    private final JwtCookieService jwtCookieService;

    public InvoiceController(BackendService backendService, JwtCookieService jwtCookieService) {
        this.backendService = backendService;
        this.jwtCookieService = jwtCookieService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        String token = jwtCookieService.extractToken(request);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            List<Map<String, Object>> invoices = backendService.getInvoices(token);
            return ResponseEntity.ok(invoices);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (ResourceAccessException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("message", "El backend de facturación no está disponible"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        String token = jwtCookieService.extractToken(request);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Map<String, Object> invoice = backendService.getInvoiceById(token, id);
            return ResponseEntity.ok(invoice);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (ResourceAccessException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("message", "El backend de facturación no está disponible"));
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> getByAppointmentId(
            @PathVariable Long appointmentId,
            HttpServletRequest request
    ) {
        String token = jwtCookieService.extractToken(request);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Map<String, Object> invoice = backendService.getInvoiceByAppointmentId(token, appointmentId);
            return ResponseEntity.ok(invoice);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (ResourceAccessException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("message", "El backend de facturación no está disponible"));
        }
    }

    @PostMapping("/appointments/{appointmentId}")
    public ResponseEntity<?> create(
            @PathVariable Long appointmentId,
            HttpServletRequest request,
            @RequestBody Map<String, Object> payload
    ) {
        String token = jwtCookieService.extractToken(request);
        if (token == null) {
            log.warn("POST /invoices/appointments/{} missing authentication token", appointmentId);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Map<String, Object> invoice = backendService.createInvoice(token, appointmentId, payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (ResourceAccessException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("message", "El backend de facturación no está disponible"));
        }
    }
}