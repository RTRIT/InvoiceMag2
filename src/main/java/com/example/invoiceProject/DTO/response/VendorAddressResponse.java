package com.example.invoiceProject.DTO.response;

import com.example.invoiceProject.Model.VendorAddress;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorAddressResponse {
    private String street;
    private String city;
    private String country;
    private String postCode;

    public VendorAddressResponse(VendorAddress address) {
        this.street = address.getStreet();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.postCode = address.getPostCode();
    }
}
