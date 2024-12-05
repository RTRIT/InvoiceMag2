package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Entity
@Getter
@Setter
@Table(name = "password_reset_token")
public class PasswordResetToken {

    private static final int EXPIRATION = 15 * 60 * 1000;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = new Date(System.currentTimeMillis() + EXPIRATION);
    }

    public PasswordResetToken() {
    }

}