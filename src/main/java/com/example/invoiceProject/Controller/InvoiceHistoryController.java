package com.example.invoiceProject.Controller;


import com.example.invoiceProject.Model.InvoiceHistory;
import com.example.invoiceProject.Service.InvoiceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoiceHistory")
public class InvoiceHistoryController {

    @Autowired
    private InvoiceHistoryService invoiceHistoryService;

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<List<InvoiceHistory>> getInvoiceHistoryByInvoiceId(@PathVariable("invoiceId") UUID invoiceId) {
        List<InvoiceHistory> invoiceHistories = invoiceHistoryService.getInvoiceHistoryByInvoiceId(invoiceId);
        return ResponseEntity.ok(invoiceHistories);
    }

    @PostMapping
    public ResponseEntity<InvoiceHistory> createInvoiceHistory(@RequestBody InvoiceHistory invoiceHistory) {
        InvoiceHistory createdInvoiceHistory = invoiceHistoryService.saveInvoiceHistory(invoiceHistory);
        return ResponseEntity.ok(createdInvoiceHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoiceHistory(@PathVariable("id") UUID id) {
        invoiceHistoryService.deleteInvoiceHistory(id);
        return ResponseEntity.ok("Invoice history deleted successfully");
    }
}
