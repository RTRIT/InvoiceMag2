package com.example.invoiceProject.Service;

import java.util.List;

import com.example.invoiceProject.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.invoiceProject.Repository.InvoiceRepository;
import com.example.invoiceProject.Repository.CurrencyRepository;
import com.example.invoiceProject.Service.PaymentTimeService;
import com.example.invoiceProject.Service.PaymentTypeService;
import com.example.invoiceProject.Service.UserService;
import com.example.invoiceProject.Service.CurrencyService;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PaymentTimeService paymentTimeService;
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private UserService userService;
    @Autowired
    private CurrencyRepository currencyRepository;


    public Invoice getInvoiceByInvoiceNo(Long invoiceNo) {
        return invoiceRepository.getInvoiceByInvoiceNo(invoiceNo);
    }
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.getAllInvoices();
    }

    public void createInvoice(Invoice invoice) {

        PaymentTime paymentTime = paymentTimeService.findPaymentTimeById(invoice.getPaymentTime().getId())
                .orElseThrow(() -> new RuntimeException("PaymentTime not found"));
        PaymentType paymentType = paymentTypeService.findPaymentTypeById(invoice.getPaymentType().getId())
                .orElseThrow(() -> new RuntimeException("PaymentTime not found"));
        User user = userService.getUserByUsername2(invoice.getUser().getEmail())
                .orElseThrow(() -> new RuntimeException("PaymentTime not found"));
        Currency currency = currencyRepository.findByCurrencyCode(invoice.getCurrency().getCurrencyCode())
                .orElseThrow(() -> new RuntimeException("PaymentTime not found"));

        invoice.setPaymentTime(paymentTime);
        invoiceRepository.save(invoice);
    }

    public void updateInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long invoiceNo) {
        invoiceRepository.deleteById(invoiceNo);
    }
}
