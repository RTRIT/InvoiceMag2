package com.example.invoiceProject.DTO.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorResponse {
    private Long vendor_id;
    private String firstname;
    private String lastname;
    private String taxIdentificationNumber;
    private String phonenumber;
    private String email;
    private String bankAccount;
    private String bank;
    private String note;
    private VendorAddressResponse vendorAddress;
}
