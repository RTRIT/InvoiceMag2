package com.example.invoiceProject.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendor_id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, name = "tax_identification_number")
    private String taxIdentificationNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String postcode;

    @Column(nullable = false)
    private String phonenumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "bank_account")
    private String bankAccount;

    @Column(nullable = false)
    private String bank;

    @Column
    private String logo;

    @OneToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @OneToMany
    private List<Invoice> invoices;
}
