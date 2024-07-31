package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.getAllInvoices();
    }

    public Invoice getInvoiceByInvoiceNo(Long invoiceNo) {
        return invoiceRepository.getInvoiceByInvoiceNo(invoiceNo);
    }

    public void createInvoice(Invoice invoice) {
        invoiceRepository.createInvoice(invoice.getInvoiceDate(), invoice.getSequenceNo(), invoice.getBuyerNoteOnInvoice(), invoice.getUnitPrice(), invoice.getPaymentMethod(), invoice.getAmount(), invoice.getVAT());
    }

    public void updateInvoice(Invoice invoice) {
        invoiceRepository.updateInvoiceByInvoiceNo(invoice.getInvoiceDate(), invoice.getSequenceNo(), invoice.getBuyerNoteOnInvoice(), invoice.getUnitPrice(), invoice.getPaymentMethod(), invoice.getAmount(), invoice.getVAT(), invoice.getInvoiceNo());
    }

    public void deleteInvoice(Long invoiceNo) {
        invoiceRepository.deleteInvoiceByInvoiceNo(invoiceNo);
    }
}
