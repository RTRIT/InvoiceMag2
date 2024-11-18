package com.example.invoiceProject.Model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "money")
public class Money {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)// Tự động tăng giá trị của id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String moneyCode; // Mã tiền tệ

    private String moneyName; // Tên tiền tệ

}

