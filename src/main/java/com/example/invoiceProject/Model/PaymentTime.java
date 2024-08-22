package com.example.invoiceProject.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "PaymentTime")
public class PaymentTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paymentTimeCode;

    @Column(nullable = false)
    private String paymentDate;

    @OneToMany(mappedBy = "paymentTime")
    private List<Invoice> invoices;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentTimeCode() {
        return paymentTimeCode;
    }

    public void setPaymentTimeCode(String paymentTimeCode) {
        this.paymentTimeCode = paymentTimeCode;
    }
}
