package com.example.invoiceProject.DTO.requests;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    private String email;
    private String password;
}
