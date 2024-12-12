package com.example.invoiceProject.DTO.requests;

import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Model.VendorAddress;

import jakarta.validation.constraints.Email;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorCreationRequest {

    @Email(message = "Email is not validated!")
    private String firstname;
    private String lastname;
    private String taxIdentificationNumber;
    private String phonenumber;
    private String email;
    private String bankAccount;
    private String bank;
    private String note;
    private VendorAddress vendorAddress;
}