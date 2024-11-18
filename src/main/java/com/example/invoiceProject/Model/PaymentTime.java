package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "PaymentTime")
public class PaymentTime {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String paymentTimeCode;

    @Column(nullable = false)
    private String paymentDate;

//    @OneToMany(mappedBy = "paymentTime")
////    private List<Invoice> invoices;

    // Getters and Setters
}
