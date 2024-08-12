package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
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

    @Column
    private String buyerNoteOnInvoice;

    @Column(nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, name = "vat")
    private Double vat;
}
