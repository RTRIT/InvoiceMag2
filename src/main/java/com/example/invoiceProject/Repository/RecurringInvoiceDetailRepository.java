package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.RecurringInvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface
RecurringInvoiceDetailRepository extends JpaRepository<RecurringInvoiceDetails, UUID> {
    Optional<RecurringInvoiceDetails> findByInvoice_InvoiceNo(UUID invoiceNo);

}
