package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "VendorAddress")
public class VendorAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String numberAddress;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private Long postCode;

    @Column
    private String fullAddress = numberAddress + " " + street + " " + city + " " + country + " " + postCode;

    public VendorAddress() {
    }

    public VendorAddress(String numberAddress, String street, String city, String country, Long postCode, String fullAddress) {
        this.numberAddress = numberAddress;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
        this.fullAddress = fullAddress;
    }

}
