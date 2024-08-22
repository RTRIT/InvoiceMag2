package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.InvoiceHistory;
import com.example.invoiceProject.Repository.InvoiceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceHistoryService {

    @Autowired
    private InvoiceHistoryRepository invoiceHistoryRepository;

    public List<InvoiceHistory> getInvoiceHistoryByInvoiceId(Long invoiceId) {
        return invoiceHistoryRepository.findByInvoice_InvoiceNo(invoiceId);
    }

    public InvoiceHistory saveInvoiceHistory(InvoiceHistory invoiceHistory) {
        return invoiceHistoryRepository.save(invoiceHistory);
    }

    public void deleteInvoiceHistory(Long id) {
        invoiceHistoryRepository.deleteById(id);
    }
}
