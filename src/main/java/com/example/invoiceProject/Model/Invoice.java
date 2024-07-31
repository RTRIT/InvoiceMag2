//package com.example.invoiceProject.Model;
//
//import jakarta.persistence.*;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "Invoice")
//public class Invoice {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long InvoiceNo;
//
//    @Column(nullable = false)
//    private LocalDate invoiceDate;
//
//    @Column(nullable = false)
//    private Integer SequenceNo;
//
//    @Column
//    private String buyerNoteOnInvoice;
//
//    @Column(nullable = false)
//    private Double UnitPrice;
//
//    @Column(nullable = false)
//    private String PaymentMethod;
//
//    @Column(nullable = false)
//    private Double Amount;
//
//    @Column(nullable = false)
//    private Double VAT;
//
//    public Long getInvoiceNo() {
//        return InvoiceNo;
//    }
//
//    public void setInvoiceNo(Long invoiceNo) {
//        InvoiceNo = invoiceNo;
//    }
//
//    public LocalDate getInvoiceDate() {
//        return invoiceDate;
//    }
//
//    public void setInvoiceDate(LocalDate invoiceDate) {
//        this.invoiceDate = invoiceDate;
//    }
//
//    public Integer getSequenceNo() {
//        return SequenceNo;
//    }
//
//    public void setSequenceNo(Integer sequenceNo) {
//        SequenceNo = sequenceNo;
//    }
//
//    public String getBuyerNoteOnInvoice() {
//        return buyerNoteOnInvoice;
//    }
//
//    public void setBuyerNoteOnInvoice(String buyerNoteOnInvoice) {
//        this.buyerNoteOnInvoice = buyerNoteOnInvoice;
//    }
//
//    public Double getUnitPrice() {
//        return UnitPrice;
//    }
//
//    public void setUnitPrice(Double unitPrice) {
//        UnitPrice = unitPrice;
//    }
//
//    public String getPaymentMethod() {
//        return PaymentMethod;
//    }
//
//    public void setPaymentMethod(String paymentMethod) {
//        PaymentMethod = paymentMethod;
//    }
//
//    public Double getAmount() {
//        return Amount;
//    }
//
//    public void setAmount(Double amount) {
//        Amount = amount;
//    }
//
//    public Double getVAT() {
//        return VAT;
//    }
//
//    public void setVAT(Double VAT) {
//        this.VAT = VAT;
//    }
//}
