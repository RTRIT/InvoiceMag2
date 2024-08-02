package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceNo;

    @Column(nullable = false)
    private LocalDate invoiceDate;

    @Column(nullable = false)
    private Integer sequenceNo;

    @Column
    private String buyerNoteOnInvoice;

    @Column(nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double VAT;

    public Long getInvoiceNo() {
        return this.invoiceNo;
    }

    public void setInvoiceNo(Long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getInvoiceDate() {
        return this.invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Integer getSequenceNo() {
        return this.sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getBuyerNoteOnInvoice() {
        return this.buyerNoteOnInvoice;
    }

    public void setBuyerNoteOnInvoice(String buyerNoteOnInvoice) {
        this.buyerNoteOnInvoice = buyerNoteOnInvoice;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getVAT() {
        return this.VAT;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }
}
