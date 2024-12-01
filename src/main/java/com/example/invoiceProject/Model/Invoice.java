package com.example.invoiceProject.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
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

    @Column(name = "buyer_note_on_invoice")
    private String buyerNoteOnInvoice;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double paid;
//
//    @ManyToMany
//    @JoinTable(
//            name = "detail_invoice",
//            joinColumns = @JoinColumn(
//                    name = "invoice_id", referencedColumnName = "invoiceNo"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "product_id", referencedColumnName = "id"))
//    private List<Product> product;

    @ManyToOne
    @JoinColumn(name = "vendorid", referencedColumnName = "vendorid")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


//    @ManyToOne
//    @JoinColumn(name = "vendorid", referencedColumnName = "vendorid")
//    private Vendor vendor;
//
//    @ManyToOne
//    @JoinColumn(name = "usermail", referencedColumnName = "gmail")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "paymentType", referencedColumnName = "id")
//    private PaymentType paymentType;
//
//    @ManyToOne
//    @JoinColumn(name = "moneyId", referencedColumnName = "id")
//    private Money money;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailInvoice> details;

}
