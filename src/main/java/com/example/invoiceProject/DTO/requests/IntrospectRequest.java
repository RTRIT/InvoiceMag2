package com.example.invoiceProject.DTO.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntrospectRequest {
    String token;
}
