package com.duoc.backend;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer appointmentId;

    private LocalDate issueDate;

    private BigDecimal vatRate;

    private BigDecimal subtotal;

    private BigDecimal vatAmount;

    private BigDecimal total;

    private String notes;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<InvoiceLineItem> items = new ArrayList<>();

    public Invoice() {
    }

    public void calculateTotals() {
        BigDecimal computedSubtotal = BigDecimal.ZERO;
        for (InvoiceLineItem item : items) {
            item.recalculateLineTotal();
            if (item.getLineTotal() != null) {
                computedSubtotal = computedSubtotal.add(item.getLineTotal());
            }
        }

        BigDecimal safeVatRate = vatRate != null ? vatRate : BigDecimal.ZERO;
        this.subtotal = computedSubtotal;
        this.vatAmount = computedSubtotal.multiply(safeVatRate);
        this.total = subtotal.add(vatAmount);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
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
