package com.example.invoiceProject.DTO.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class InvoiceRequest {
    private UUID invoiceNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate invoiceDate;
    private Long sequenceNo;
    private Double netTotal ;
    private Double vatTotal ;
    private Double grossTotal ;
    private String buyerNoteOnInvoice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate paymentTime;
    private String paymentType;
    private String status;
    private Double paid;
    private Boolean isRecurring;
        // Getters và Setters


    public InvoiceRequest() {
    }

    public InvoiceRequest(UUID invoiceNo, LocalDate invoiceDate, Long sequenceNo, Double netTotal, Double grossTotal, Double vatTotal, String buyerNoteOnInvoice, LocalDate paymentTime, String status, String paymentType, Double paid) {
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.sequenceNo = sequenceNo;
        this.netTotal = netTotal;
        this.grossTotal = grossTotal;
        this.vatTotal = vatTotal;
        this.buyerNoteOnInvoice = buyerNoteOnInvoice;
        this.paymentTime = paymentTime;
        this.status = status;
        this.paymentType = paymentType;
        this.paid = paid;
    }
}
