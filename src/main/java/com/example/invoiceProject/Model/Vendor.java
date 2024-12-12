package com.example.invoiceProject.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false, name = "tax_identification_number")
    private String taxIdentificationNumber;

    @Column(nullable = false)
    private String phonenumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, name = "bank_account")
    private String bankAccount;

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private int status = 1;

    // @Column
    // private String logo;

    @Column
    private String note;

    @OneToMany
    private List<Invoice> invoices;

    @OneToOne(cascade = CascadeType.ALL) // Cascade to automatically persist VendorAddress when Vendor is saved
    @JoinColumn(name = "addr", referencedColumnName = "id", nullable = true) // Allowing nullable for optional address
    private VendorAddress vendorAddress; // Changed to lowercase for consistency
}
