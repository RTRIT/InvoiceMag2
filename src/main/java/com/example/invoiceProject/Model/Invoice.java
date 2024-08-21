package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import com.example.invoiceProject.Model.Currency;
import com.example.invoiceProject.Model.PaymentType;
import com.example.invoiceProject.Model.PaymentTime;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceNo;

    @Column(nullable = false)
    private LocalDate invoiceDate;

    @Column(nullable = false)
    private Integer sequenceNo;

    @Column(nullable = false)
    private Double netTotal ;

    @Column(nullable = false)
    private Double vatTotal ;

    @Column(nullable = false)
    private Double grossTotal ;

    @Column
    private String buyerNoteOnInvoice;

    @Column(nullable = false)
    private String status;

    public Long getInvoiceNo() {
        return invoiceNo;
    }
    @Column(nullable = false)
    private Double paid;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "currencyId")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "paymentType", referencedColumnName = "id")
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "paymentTime", referencedColumnName = "id")
    private PaymentTime paymentTime;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailInvoice> invoiceItems;

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public String getBuyerNoteOnInvoice() {
        return buyerNoteOnInvoice;
    }


    public void setInvoiceNo(Long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public void setBuyerNoteOnInvoice(String buyerNoteOnInvoice) {
        this.buyerNoteOnInvoice = buyerNoteOnInvoice;
    }

    public String getStatus() {
        return status;
    }

    public Double getPaid() {
        return paid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getNetTotal() {
        return netTotal;
    }

    public Double getVatTotal() {
        return vatTotal;
    }

    public Double getGrossTotal() {
        return grossTotal;
    }

    public void setNetTotal(Double netTotal) {
        this.netTotal = netTotal;
    }

    public void setVatTotal(Double vatTotal) {
        this.vatTotal = vatTotal;
    }

    public void setGrossTotal(Double grossTotal) {
        this.grossTotal = grossTotal;
    }

    public PaymentTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(PaymentTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public User getUser() {
        return user;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
