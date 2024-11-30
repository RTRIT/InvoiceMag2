package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.*;
import com.example.invoiceProject.Repository.InvoiceRepository;
import com.example.invoiceProject.Repository.VendorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder.In;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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


    public Invoice getInvoiceByInvoiceNo(UUID invoiceNo) {
        return invoiceRepository.getInvoiceByInvoiceNo(invoiceNo);
    }
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.getAllInvoices();
    }

    public void createInvoice(Invoice invoice) {
        
        // Tìm PaymentType từ ID
        PaymentType paymentType = paymentTypeService.findPaymentTypeById(invoice.getPaymentType().getId())
                .orElseThrow(() -> new RuntimeException("PaymentType not found"));
    
        // Tìm User từ ID
        User user = userService.getUserById(invoice.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
//        Date paymentTime = paymentTimeService.findPaymentTimeById(invoice.getPaymentTime())
//                .orElseThrow(() -> new RuntimeException("PaymentTime not found"));
    
        // Tìm Money từ ID
        Money money = moneyService.findMoneyById(invoice.getMoney().getId())
                .orElseThrow(() -> new RuntimeException("Money not found"));
    
        // Tìm các Vendor theo id
        // Vendor vendor = vendorRepository.findByVendorid(invoice.getVendor().getVendorid())
        //         .orElseThrow(() -> new EntityNotFoundException("Vendor not found"));

        Vendor vendor = vendorRepository.findByEmail(invoice.getVendor().getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found"));

        
        invoice.setPaymentType(paymentType);
        invoice.setUser(user);
        invoice.setMoney(money);
        invoice.setVendor(vendor);
    
        // Lưu hóa đơn vào cơ sở dữ liệu
        invoiceRepository.save(invoice);
    }
    

    public void updateInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(UUID invoiceNo) {
        invoiceRepository.deleteInvoiceByInvoiceNo(invoiceNo);
    }
}
