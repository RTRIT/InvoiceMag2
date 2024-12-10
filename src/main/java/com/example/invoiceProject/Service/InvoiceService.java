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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
    @Autowired
    private TemplateEngine templateEngine;


    public Invoice getInvoiceByInvoiceNo(UUID invoiceNo) {
        return invoiceRepository.getInvoiceByInvoiceNo(invoiceNo);
    }
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice createInvoice(Invoice invoice) {
        Long maxSequenceNo = invoiceRepository.findMaxSequenceNo();
        invoice.setSequenceNo((maxSequenceNo == null) ? 1 : maxSequenceNo + 1);
        return invoiceRepository.save(invoice);
    }


    public String generateInvoiceHtml(Product product, Invoice invoice, Vendor vendor, DetailInvoice detailInvoice, Department department) {
        // Tạo một đối tượng Context chứa các dữ liệu cần thiết cho template
        Context context = new Context();
        context.setVariable("product", product);
        context.setVariable("invoice", invoice);
        context.setVariable("detailInvoice", detailInvoice );
        context.setVariable("vendor", vendor);
        context.setVariable("invoice.department", department);
//        context.setVariable("user", vendor);

        // Sử dụng Thymeleaf để tạo HTML từ template (template là file .html hoặc chuỗi HTML)
        return templateEngine.process("/invoice/inf", context);  // invoiceTemplate là tên template của bạn
    }



    public void updateInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(UUID invoiceNo) {
        invoiceRepository.deleteInvoiceByInvoiceNo(invoiceNo);
    }
}
