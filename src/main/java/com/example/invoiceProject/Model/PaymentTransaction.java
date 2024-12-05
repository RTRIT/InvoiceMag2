package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTransaction {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String txnRef; // Mã tham chiếu tới giao dịch trên merchant đó

    @Column
    private String invoiceId;

    @Column
    private String vnpBankTransNo; // Mã giao dịch tại Ngân hàng

    @Column
    private String vnpBankCode;

    @Column
    private LocalDateTime vnpPayDate;

    @Column
    private String respCode;

    @Column
    private String orderInfo; // get invoice id from here


}
