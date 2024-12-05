package com.example.invoiceProject.Service;

import com.example.invoiceProject.DTO.response.VendorResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
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


    public Invoice getInvoiceByInvoiceNo(UUID invoiceNo) {
        return invoiceRepository.getInvoiceByInvoiceNo(invoiceNo);
    }
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }


    public void updateInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(UUID invoiceNo) {
        invoiceRepository.deleteInvoiceByInvoiceNo(invoiceNo);
    }
}
