package com.duoc.backend;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class InvoiceLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    @JsonBackReference
    private Invoice invoice;

    @Enumerated(EnumType.STRING)
    private InvoiceLineItemType type;

    private String description;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal lineTotal;

    public InvoiceLineItem() {
    }

    public InvoiceLineItem(InvoiceLineItemType type, String description, Integer quantity, BigDecimal unitPrice) {
        this.type = type;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public void recalculateLineTotal() {
        BigDecimal safeUnitPrice = unitPrice != null ? unitPrice : BigDecimal.ZERO;
        int safeQuantity = quantity != null ? quantity : 0;
        this.lineTotal = safeUnitPrice.multiply(BigDecimal.valueOf(safeQuantity));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public InvoiceLineItemType getType() {
        return type;
    }

    public void setType(InvoiceLineItemType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }
}
