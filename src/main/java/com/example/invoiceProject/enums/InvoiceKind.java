package com.example.invoiceProject.enums;


import lombok.Getter;
import org.springframework.context.annotation.Bean;

@Getter
public enum InvoiceKind {

    INVOICE,
    RECURRENCE,
    PROFORMA,


}
