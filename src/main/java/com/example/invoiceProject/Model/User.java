package com.example.invoiceProject.Model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;


//    @OneToOne
//    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
//    private Role role;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Department department;


    @Column(name = "FirstName")
    private String firstName;

    @Column
    private String lastName;


    @Column(updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column
    @LastModifiedDate
    private Date updatedAt;


    public User(String mail, String password, Role role) {
        this.email = mail;
        this.role = role;
        this.password = password;
    }
}
