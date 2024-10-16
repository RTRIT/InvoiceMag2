package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Role")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

//    @ManyToMany
//    @JoinTable(
//            name = "privilege_detail",
//            joinColumns = @JoinColumn(name = "roleId"),
//            inverseJoinColumns = @JoinColumn(name = "privilegeId")
//    )
//    List<Privilege> privileges;
    @ManyToMany
    Set<Privilege> privileges;


    @Column(unique = true)
    String roleName;

}
