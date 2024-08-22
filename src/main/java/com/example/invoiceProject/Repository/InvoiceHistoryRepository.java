package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.InvoiceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceHistoryRepository extends JpaRepository<InvoiceHistory, Long> {
    List<InvoiceHistory> findByInvoice_InvoiceNo(Long invoiceId);
}

