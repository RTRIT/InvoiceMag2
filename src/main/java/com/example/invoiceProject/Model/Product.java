package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double tax;

    @Column(nullable = false)
    private Double grossPrice;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String description;

    public Product(){};

    public Product(String name, String code, Double price, Double tax, Double grossPrice, String currency, String description) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.tax = tax;
        this.grossPrice = 0.0;
        this.currency = currency;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(Double grossPrice) {
        this.grossPrice = grossPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

