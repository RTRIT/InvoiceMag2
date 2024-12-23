package com.example.invoiceProject.Util;

import com.example.invoiceProject.enums.InvoiceKind;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FilterCriteria {
    private Long sequenceNo;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String paymentType;
//    private InvoiceKind kind;
}
