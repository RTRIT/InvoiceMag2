package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.PaymentTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentTimeRepository extends JpaRepository<PaymentTime, Long> {
    Optional<PaymentTime> findByPaymentTimeCode(String paymentTimeCode);
    List<PaymentTime> findAllByPaymentTimeCodeIn(List<String> paymentTimeCodes);
}