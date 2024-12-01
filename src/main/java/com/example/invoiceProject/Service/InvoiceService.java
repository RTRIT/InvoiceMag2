package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.*;
import com.example.invoiceProject.Repository.InvoiceRepository;
import com.example.invoiceProject.Repository.VendorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder.In;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
//    @Autowired
//    private PaymentTimeService paymentTimeService;
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private UserService userService;
    @Autowired
    private MoneyService moneyService;
    @Autowired
    private VendorRepository vendorRepository;


//    public Optional<Invoice> getInvoiceByInvoiceNo(UUID invoiceNo) {
//        return invoiceRepository.findByInvoiceNo(invoiceNo);
//    }
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public void createInvoice(Invoice invoice) {

//        System.out.println("Get into createInvoice function");
//        PaymentType paymentType = paymentTypeService.findPaymentTypeById(invoice.getPaymentType().getId())
//                .orElseThrow(() -> new RuntimeException("PaymentType not found"));
//        Date paymentTime = invoice.getPaymentTime();
////        Date paymentTime = paymentTimeService.findPaymentTimeById(invoice.getPaymentTime())
////                .orElseThrow(() -> new RuntimeException("PaymentTime not found"));
//        User user = userService.getUserById(invoice.getUser().getId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        Money money = moneyService.findMoneyById(invoice.getMoney().getId())
//                .orElseThrow(() -> new RuntimeException("Money not found"));
//
//        invoice.setPaymentTime(paymentTime);
//        invoice.setPaymentType((paymentType));
//        invoice.setUser(user);
//        invoice.setMoney(money);
        invoiceRepository.save(invoice);
    }

    public void updateInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(UUID invoiceNo) {
        invoiceRepository.deleteInvoiceByInvoiceNo(invoiceNo);
    }
}
