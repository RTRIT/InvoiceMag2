package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository  extends JpaRepository<InvalidToken, String> {

}
