package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Double price;

//    @Column(nullable = false)
//    private Double tax;

//    @Column(nullable = false)
//    private Double grossPrice;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "product")
    private List<DetailInvoice> detailInvoice;

    public Product(){};

//    public Product(String name, String code, Double price, Double tax, Double grossPrice, String currency, String description) {
//        this.name = name;
//        this.code = code;
//        this.price = price;
//        this.tax = tax;
//        this.grossPrice = 0.0;
//        this.currency = currency;
//        this.description = description;
//    }

    // Getters and Setters

}

