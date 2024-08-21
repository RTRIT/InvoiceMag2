package com.example.invoiceProject.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "PaymentType")
public class PaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false)
    private String paymentTypeCode;

    @Column(nullable = false)
    private String typeName;

    @OneToMany(mappedBy = "paymentType")
    private List<Invoice> invoices;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    public void setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
    }
}
