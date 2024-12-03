package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Double price;

   @Column(nullable = false)
   private Double tax;
//
//    @Column(nullable = false)
//    private Double grossPrice;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "product")
    private List<DetailInvoice> detailInvoice;

}

