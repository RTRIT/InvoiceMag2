package com.example.invoiceProject.Model.Payment;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VnPay {
    private String code;
    private String message;
    private String paymentUrl;
}
