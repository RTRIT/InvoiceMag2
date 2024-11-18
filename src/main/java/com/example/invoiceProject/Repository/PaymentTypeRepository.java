package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, UUID> {
    Optional<PaymentType> findByPaymentTypeCode(String paymentTypeCode);
    List<PaymentType> findAllByPaymentTypeCodeIn(List<String> paymentTypeCodes);
}
