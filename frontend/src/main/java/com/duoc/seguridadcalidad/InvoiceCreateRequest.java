package com.duoc.seguridadcalidad;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceCreateRequest {
    private LocalDate issueDate;
    private BigDecimal vatRate;
    private String notes;
    private List<InvoiceLineItem> items = new ArrayList<>();

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<InvoiceLineItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceLineItem> items) {
        this.items = items;
    }
}