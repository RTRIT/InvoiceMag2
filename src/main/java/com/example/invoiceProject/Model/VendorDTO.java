package com.example.invoiceProject.Model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorDTO {
    private String firstname;
    private String lastname;
    private String taxIdentificationNumber;
    private String address;
    private String street;
    private String city;
    private String country;
    private String postcode;
    private String phonenumber;
    private String email;
    private String bankAccount;
    private String bank;
    private String logo;
    private Long typeID;

}

