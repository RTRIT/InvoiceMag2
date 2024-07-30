package com.example.invoiceProject.Model;

import jakarta.persistence.*;
@Entity
@Table(name = "Vendor")

public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String tax_identification_number;
    private String address;
    private String street;
    private String city;
    private String country;
    private String postcode;
    private String phonenumber;
    private String email;
    private String bankAcount;
    private String bank;
    private String logo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTax_identification_number() {
        return tax_identification_number;
    }

    public void setTax_identification_number(String tax_identification_number) {
        this.tax_identification_number = tax_identification_number;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLax_identification_number() {
        return tax_identification_number;
    }

    public void setLax_identification_number(String lax_identification_number) {
        this.tax_identification_number = lax_identification_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankAcount() {
        return bankAcount;
    }

    public void setBankAcount(String bankAcount) {
        this.bankAcount = bankAcount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
