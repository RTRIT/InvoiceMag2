package com.example.invoiceProject.DTO.response;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    boolean authenticated;
}
