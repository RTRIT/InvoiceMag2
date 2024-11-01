package com.example.invoiceProject.DTO.requests;

import java.util.Map;

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
    private VendorAddress address;

    public VendorCreationRequest(Map<String, Object> vendorData) {
        this.firstname = (String) vendorData.get("firstname");
        this.lastname = (String) vendorData.get("lastname");
        this.taxIdentificationNumber = (String) vendorData.get("taxIdentificationNumber");
        this.phonenumber = (String) vendorData.get("phonenumber");
        this.email = (String) vendorData.get("email");
        this.bankAccount = (String) vendorData.get("bankAccount");
        this.bank = (String) vendorData.get("bank");
        this.note = (String) vendorData.get("note");

        if (vendorData.containsKey("vendorAddress")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> addressData = (Map<String, Object>) vendorData.get("vendorAddress");
            this.address = new VendorAddress();
            this.address.setStreet((String) addressData.get("street"));
            this.address.setCity((String) addressData.get("city"));
            this.address.setCountry((String) addressData.get("country"));
            this.address.setPostCode((String) addressData.get("postCode"));
        }
    }
}