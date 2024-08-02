package com.example.invoiceProject.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Repository.InvoiceRepository;

@Service
public class InvoiceSerice {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice getInvoiceByInvoiceNo(Long invoiceNo) {
        return invoiceRepository.getInvoiceByInvoiceNo(invoiceNo);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.getAllInvoices();
    }

    public void createInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void updateInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long invoiceNo) {
        invoiceRepository.deleteById(invoiceNo);
    }
}
