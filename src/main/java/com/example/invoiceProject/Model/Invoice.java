
package com.example.invoiceProject.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID invoiceNo;

    @Column(nullable = false)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate invoiceDate;

    @Column
    private Long sequenceNo;

    @Column(nullable = false)
    private Double netTotal ;

    @Column(nullable = false)
    private Double vatTotal ;

    @Column(nullable = false)
    private Double grossTotal ;

    @Column
    private String buyerNoteOnInvoice;

    @Column(nullable = false)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate paymentTime;

    @Column(nullable = false)
    private String paymentType;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double paid;

    @Column(nullable = false)
    @JsonIgnore
    private Integer statusExit;

    @ManyToOne
    @JoinColumn(name = "usermail", referencedColumnName = "email")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "vendormail", referencedColumnName = "email", nullable = false)
    @JsonIgnore
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "departmentmail", referencedColumnName = "email", nullable = false)
    @JsonIgnore
    private Department department;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DetailInvoice> details = new ArrayList<>();

}