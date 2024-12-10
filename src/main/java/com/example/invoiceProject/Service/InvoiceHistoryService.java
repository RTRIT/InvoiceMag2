package com.example.invoiceProject.Service;

import com.example.invoiceProject.DTO.requests.InvoiceRequest;
import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Model.InvoiceHistory;
import com.example.invoiceProject.Repository.InvoiceHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceHistoryService {

    @Autowired
    private InvoiceHistoryRepository invoiceHistoryRepository;

    public List<InvoiceHistory> getInvoiceHistoryByInvoiceId(UUID invoiceId) {
        return invoiceHistoryRepository.findByInvoiceid(invoiceId);
    }

    public InvoiceHistory saveInvoiceHistory(InvoiceHistory invoiceHistory) {
        return invoiceHistoryRepository.save(invoiceHistory);
    }

    public void deleteInvoiceHistory(UUID id) {
        invoiceHistoryRepository.deleteById(id);
    }

    public void saveInvoiceToHistory(Invoice invoice, String userEmail, String action) {
        InvoiceHistory history = new InvoiceHistory();
        history.setInvoiceid(invoice.getInvoiceNo());
        history.setUserEmail(userEmail);
        history.setAct(action);
        history.setCreatedAt(LocalDateTime.now());

        // Chuyển đổi hóa đơn thành JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String invoiceJson = objectMapper.writeValueAsString(invoice);
            history.setInvoiceData(invoiceJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting invoice to JSON", e);
        }
        invoiceHistoryRepository.save(history);
    }

    public static InvoiceRequest mapToDTO(Invoice invoice) {
        return new InvoiceRequest(
                invoice.getInvoiceNo(),
                invoice.getInvoiceDate(),
                invoice.getSequenceNo(),
                invoice.getNetTotal(),
                invoice.getVatTotal(),
                invoice.getGrossTotal(),
                invoice.getBuyerNoteOnInvoice(),
                invoice.getPaymentTime(),
                invoice.getPaymentType(),
                invoice.getStatus(),
                invoice.getPaid());
    }
}
