package com.example.invoiceProject.DTO.requests;

import com.example.invoiceProject.Model.Invoice;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecurringInvoiceDetailRequest {
    Invoice invoice;
    String recurrenceType;
    Integer recurrenceInterval;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate nextInvoiceDate;
    Integer totalOccurrences;
    LocalDate lastGeneratedDate;

}
