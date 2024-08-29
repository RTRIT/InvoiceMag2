package com.example.invoiceProject.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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


    public Privilege(String name, String desc){
        this.privilegeName=name;
        this.privilegeDesc=desc;
    }



}
