package com.example.invoiceProject.DTO.response;

import java.util.UUID;
import com.example.invoiceProject.Model.Vendor;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VendorResponse {
    private UUID vendorid;
    private String firstname;
    private String lastname;
    private String taxIdentificationNumber;
    private String phonenumber;
    private String email;
    private String bankAccount;
    private String bank;
    private String note;
    private VendorAddressResponse vendorAddress;

    public VendorResponse(Vendor vendor) {
        this.vendorid = vendor.getVendorid();
        this.firstname = vendor.getFirstname();
        this.lastname = vendor.getLastname();
        this.taxIdentificationNumber = vendor.getTaxIdentificationNumber();
        this.phonenumber = vendor.getPhonenumber();
        this.email = vendor.getEmail();
        this.bankAccount = vendor.getBankAccount();
        this.bank = vendor.getBank();
        this.note = vendor.getNote();
        if (vendor.getVendorAddress() != null) {
            this.vendorAddress = new VendorAddressResponse(vendor.getVendorAddress());
        }
    }
}
