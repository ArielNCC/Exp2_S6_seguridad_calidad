package com.duoc.seguridadcalidad;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private Long id;
    private Long appointmentId;
    private LocalDate issueDate;
    private BigDecimal vatRate;
    private BigDecimal subtotal;
    private BigDecimal vatAmount;
    private BigDecimal total;
    private String notes;
    private List<InvoiceLineItem> items = new ArrayList<>();

    public Invoice() {
    }

    public Invoice(Long id, Long appointmentId, LocalDate issueDate, BigDecimal vatRate, BigDecimal subtotal,
                   BigDecimal vatAmount, BigDecimal total, String notes, List<InvoiceLineItem> items) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.issueDate = issueDate;
        this.vatRate = vatRate;
        this.subtotal = subtotal;
        this.vatAmount = vatAmount;
        this.total = total;
        this.notes = notes;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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