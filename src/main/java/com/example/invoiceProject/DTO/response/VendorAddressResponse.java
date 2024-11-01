package com.example.invoiceProject.DTO.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorAddressResponse {
    private Long address_id;
    private String street;
    private String city;
    private String country;
    private String postCode;
    
}
