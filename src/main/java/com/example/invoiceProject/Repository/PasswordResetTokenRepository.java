package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);


    PasswordResetToken getPasswordResetTokenByToken(String token);
}