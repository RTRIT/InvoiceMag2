package com.example.invoiceProject.Model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "Vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID vendorid;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = true, name = "tax_identification_number")
    private String taxIdentificationNumber;

    @Column(nullable = false)
    private String phonenumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "bank_account")
    private String bankAccount;

    @Column(nullable = false)
    private String bank;

    @Column
    private String note;

    @OneToMany
    private List<Invoice> invoices;

    @OneToOne(cascade = CascadeType.ALL) // Cascade to automatically persist VendorAddress when Vendor is saved
    @JoinColumn(name = "addr", referencedColumnName = "id", nullable = true) // Allowing nullable for optional address
    private VendorAddress vendorAddress; // Changed to lowercase for consistency

    // Default constructor
    public Vendor() {
    }
    // Constructor without individual address fields
    public Vendor(String firstname, String lastname, String taxIdentificationNumber, String phonenumber, String email, String bankAccount, String bank, VendorAddress vendorAddress, String note) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.phonenumber = phonenumber;
        this.email = email;
        this.bankAccount = bankAccount;
        this.bank = bank;
        //this.logo = logo;
        this.vendorAddress = vendorAddress;
        this.note = note;
    }
}
