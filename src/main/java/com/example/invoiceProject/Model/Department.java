package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nameDepartment;

    @Column
    private String taxId;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private String bank;

    @Column
    private String bankAccount;

}
