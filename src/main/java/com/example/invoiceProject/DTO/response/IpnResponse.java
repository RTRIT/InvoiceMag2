package com.example.invoiceProject.DTO.response;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IpnResponse {
    private String responseCode;
    private String message;
}
