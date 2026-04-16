package com.duoc.seguridadcalidad;

import java.math.BigDecimal;

public class InvoiceLineItem {
    private Long id;
    private InvoiceLineItemType type;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;

    public InvoiceLineItem() {
    }

    public InvoiceLineItem(Long id, InvoiceLineItemType type, String description, Integer quantity, BigDecimal unitPrice, BigDecimal lineTotal) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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