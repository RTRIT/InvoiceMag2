//package com.example.invoiceProject.Model;
//
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDate;
//import java.util.UUID;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "recurring_invoice_details")
//public class RecurringInvoiceDetails {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(columnDefinition = "BINARY(16)")
//    private UUID id;
//
//    @OneToOne
//    @JoinColumn(name = "invoice_id", nullable = false)
//    private Invoice invoice;
//
//    @Column(nullable = false)
//    private String recurrenceType; // e.g., daily, weekly, monthly, annually
//
//    @Column
//    private Integer recurrenceInterval; // e.g., 1 (every month), 2 (every 2 months)
//
//    @Column
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    private LocalDate startDate; //The start of the recurring schedule
//
//    @Column
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    private LocalDate endDate; //The end of the recurring schedule
//
//    @Column
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    private LocalDate nextInvoiceDate; //The next date an invoice will be generated
//
//    @Column
//    private Integer totalOccurrences; // Limits the total numbers if invoice to generate
//
//    @Column
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    private LocalDate lastGeneratedDate; //Track when the last invoice was generated
//}
