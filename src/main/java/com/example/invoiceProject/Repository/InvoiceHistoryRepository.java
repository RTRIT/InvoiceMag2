package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.InvoiceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InvoiceHistoryRepository extends JpaRepository<InvoiceHistory, UUID> {
    List<InvoiceHistory> findByInvoiceid(UUID invoiceid);
}

