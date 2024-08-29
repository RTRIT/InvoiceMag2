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

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private Type type;

    @OneToMany
    private List<Invoice> invoices;

    //create constructor
    public Vendor() {
    }

    public Vendor(String firstname, String lastname, String taxIdentificationNumber, String address, String street, String city, String country, String postcode, String phonenumber, String email, String bankAccount, String bank, String logo, Type type) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.address = address;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postcode = postcode;
        this.phonenumber = phonenumber;
        this.email = email;
        this.bankAccount = bankAccount;
        this.bank = bank;
        this.logo = logo;
        this.type = type;
    }

}
