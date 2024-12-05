
package com.example.invoiceProject.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.invoiceProject.Model.Money;
import com.example.invoiceProject.Model.PaymentType;
//import com.example.invoiceProject.Model.PaymentTime;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID invoiceNo;

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
    private LocalDate paymentTime;

    @Column(nullable = false)
    private String paymentType;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double paid;

    @ManyToOne
    @JoinColumn(name = "usermail", referencedColumnName = "email")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vendormail", referencedColumnName = "email", nullable = false)
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "departmentmail", referencedColumnName = "email", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailInvoice> details = new ArrayList<>();

}