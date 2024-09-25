package com.example.invoiceProject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRestDTO {
    private String status;
    private String message;
    private String data;
}
