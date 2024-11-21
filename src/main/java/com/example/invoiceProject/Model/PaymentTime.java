package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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
    private Date paymentDate;

//    @OneToMany(mappedBy = "paymentTime")
////    private List<Invoice> invoices;

    // Getters and Setters
}
