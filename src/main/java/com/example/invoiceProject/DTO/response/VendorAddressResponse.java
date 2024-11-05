package com.example.invoiceProject.DTO.response;
import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorAddressResponse {
    private UUID vendorAddressUuid;
    private String street;
    private String city;
    private String country;
    private String postCode;
    
}
