package com.example.invoiceProject.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.List;


@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "privilege")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToMany(mappedBy = "privileges")
//    List<Role> roles;

    @Column
    private String privilegeName;

    @Column
    private String privilegeDesc;



}
