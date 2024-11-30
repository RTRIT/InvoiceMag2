package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "PaymentTime")
public class PaymentTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paymentTimeCode;

    @Column(nullable = false)
    private LocalDate paymentDate;

//    @OneToMany(mappedBy = "paymentTime")
////    private List<Invoice> invoices;

    // Getters and Setters
}
