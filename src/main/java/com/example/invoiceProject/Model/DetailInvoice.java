package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "DetailInvoices")
public class DetailInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = true)
    private String productCode;

    @Column(nullable = false)
    private Double productPrice;

    @Column(nullable = false)
    private Double productTax;

    @Column(nullable = false)
    private Double productGrossPrice;

    @Column(nullable = false)
    private String productCurrency;

    @Column(nullable = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoiceNo")
    private Invoice invoice;

}
