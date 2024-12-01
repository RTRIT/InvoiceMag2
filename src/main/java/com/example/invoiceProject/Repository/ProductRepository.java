package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.PaymentType;
import com.example.invoiceProject.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
        List<Product> findAll();
        Optional<Product> findById(UUID id);
        List<Product> findAllByIdIn(List<UUID> id);

}


