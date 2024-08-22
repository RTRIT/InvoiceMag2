package com.example.invoiceProject.Model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "money")
public class Money {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng giá trị của id
    private Long id;

    private String moneyCode; // Mã tiền tệ

    private String moneyName; // Tên tiền tệ

}

