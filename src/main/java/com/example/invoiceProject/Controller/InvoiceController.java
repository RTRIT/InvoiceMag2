package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    @ResponseBody
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{invoiceNo}")
    public ResponseEntity<Invoice> getInvoiceByInvoiceNo(@PathVariable UUID invoiceNo) {
        Invoice invoice = invoiceService.getInvoiceByInvoiceNo(invoiceNo);
        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        System.out.println("Geet into createInvoice Controller!!!");
        invoiceService.createInvoice(invoice);
        return ResponseEntity.ok(invoice);
    }

    @PutMapping("/{invoiceNo}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable UUID invoiceNo, @RequestBody Invoice invoice) {
        invoice.setInvoiceNo(invoiceNo);
        invoiceService.updateInvoice(invoice);
        return ResponseEntity.ok(invoice);
    }

    @DeleteMapping("/{invoiceNo}")
    public ResponseEntity deleteInvoice(@PathVariable UUID invoiceNo) {
        invoiceService.deleteInvoice(invoiceNo);
        return ResponseEntity.ok().build();
    }

}
