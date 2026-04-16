package com.duoc.seguridadcalidad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class InvoicePageController {

    @GetMapping("/billing")
    public String listInvoices() {
        return "invoices";
    }

    @GetMapping("/billing/new")
    public String showCreateForm() {
        return "new_invoice";
    }

    @GetMapping("/billing/{id}")
    public String showInvoiceDetail(@PathVariable Long id) {
        return "invoice_detail";
    }
}