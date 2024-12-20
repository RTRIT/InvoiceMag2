package com.example.invoiceProject.Model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "User")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;



    @Column(nullable = false, unique = true)
    @Email(message = "Email should be validated")
    private String email;

    @Column(nullable = false)
    @Size(min = 8, message = "Password is at least 8 character")
    private String password;


//    @OneToOne
//    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
//    private Role role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "roles_id", referencedColumnName = "id"))
    List<Role> roles;
//    private List<Role> role;


    @ManyToOne
    private Department department;


    @Column(name = "FirstName")
    private String firstName;

    @Column
    private String lastName;

//    @OneToMany(mappedBy = "user")
//    private List<Invoice> invoices;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private Integer exitStatus;


    public User(String mail, String password, List<Role> roles) {
        this.email = mail;
        this.roles = roles;
        this.password = password;
    }


}