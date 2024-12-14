package com.example.invoiceProject.DTO.requests;

import com.example.invoiceProject.Model.VendorAddress;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorUpdateReq {
    
    private String firstname;
    private String lastname;
    private String taxIdentificationNumber;
    private String bankAccount;
    private String bank;
    private String note;
    private VendorAddress vendorAddress;

}
