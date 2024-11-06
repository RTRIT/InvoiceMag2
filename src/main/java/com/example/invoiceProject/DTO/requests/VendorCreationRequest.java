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

    public Vendor toVendorEntity(VendorCreationRequest request) {
        Vendor vendor = new Vendor();
        vendor.setFirstname(request.getFirstname());
        vendor.setLastname(request.getLastname());
        vendor.setTaxIdentificationNumber(request.getTaxIdentificationNumber());
        vendor.setPhonenumber(request.getPhonenumber());
        vendor.setEmail(request.getEmail());
        vendor.setBankAccount(request.getBankAccount());
        vendor.setBank(request.getBank());
        vendor.setNote(request.getNote());

        // Ánh xạ địa chỉ
        if (request.getVendorAddress() != null) {
            VendorAddress vendorAddress = new VendorAddress();
            vendorAddress.setStreet(request.getVendorAddress().getStreet());
            vendorAddress.setCity(request.getVendorAddress().getCity());
            vendorAddress.setCountry(request.getVendorAddress().getCountry());
            vendorAddress.setPostCode(request.getVendorAddress().getPostCode());
            vendor.setVendorAddress(vendorAddress);
        }

        return vendor;
    }
}