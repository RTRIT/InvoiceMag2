package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.*;
import com.example.invoiceProject.Service.MoneyService;
import com.example.invoiceProject.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private MoneyService moneyService;


    public Invoice getInvoiceByInvoiceNo(Long invoiceNo) {
        return invoiceRepository.getInvoiceByInvoiceNo(invoiceNo);
    }
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.getAllInvoices();
    }

    public void createInvoice(Invoice invoice) {

        PaymentType paymentType = paymentTypeService.findPaymentTypeById(invoice.getPaymentType().getId())
                .orElseThrow(() -> new RuntimeException("PaymentType not found"));
        PaymentTime paymentTime = paymentTimeService.findPaymentTimeById(invoice.getPaymentTime().getId())
                .orElseThrow(() -> new RuntimeException("PaymentTime not found"));
        User user = userService.getUserByUsername(invoice.getUser().getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Money money = moneyService.findMoneyById(invoice.getMoney().getId())
                .orElseThrow(() -> new RuntimeException("Money not found"));

        invoice.setPaymentTime(paymentTime);
        invoice.setPaymentType((paymentType));
        invoice.setUser(user);
        invoice.setMoney(money);
        invoiceRepository.save(invoice);
    }

    public void updateInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long invoiceNo) {
        invoiceRepository.deleteById(invoiceNo);
    }
}
