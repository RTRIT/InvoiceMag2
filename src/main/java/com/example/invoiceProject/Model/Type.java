package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long type_id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "type")
    private List<Vendor> vendors;
}
