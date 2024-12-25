package com.example.invoiceProject.Service;

import com.example.invoiceProject.DTO.response.VendorResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.*;
import com.example.invoiceProject.Repository.InvoiceRepository;
import com.example.invoiceProject.Repository.VendorRepository;

import com.example.invoiceProject.Util.FilterCriteria;
//import com.example.invoiceProject.Util.InvoiceSpecification;
import com.example.invoiceProject.enums.InvoiceKind;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder.In;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Optional;
import java.util.logging.Filter;

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


    @PreAuthorize("hasAuthority('VIEW_INVOICE') or hasAuthority('UPDATE_INVOICE')")
    public Invoice getInvoiceByInvoiceNo(UUID invoiceNo) {
        return invoiceRepository.getInvoiceByInvoiceNo(invoiceNo);
    }


    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @PreAuthorize("hasAuthority('CREATE_INVOICE')")
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



    @PreAuthorize("hasAuthority('UPDATE_INVOICE')")
    public Invoice updateInvoice(Invoice invoice) {
       return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(UUID invoiceNo) {
        invoiceRepository.deleteInvoiceByInvoiceNo(invoiceNo);
    }

    public Object[] getTodayRevenue() {
        Double price = Optional.ofNullable(invoiceRepository.getTotalRevenueByDay(LocalDate.now())).orElse(0.0);
        Integer sl = invoiceRepository.getCountRevenueByDay(LocalDate.now());
        Object[] result = new Object[]{price ,sl};
        return result;
    }

    public Object[] getThisWeekRevenue() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(java.time.DayOfWeek.SUNDAY);
        Double price = Optional.ofNullable(invoiceRepository.getTotalRevenueByRange(startOfWeek, endOfWeek)).orElse(0.0);;
        Integer sl = invoiceRepository.getCountRevenueByRange(startOfWeek, endOfWeek);
        Object[] result = new Object[]{price ,sl};
        return result;
    }

    public Object[] getThisMonthRevenue() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        Double price = Optional.ofNullable(invoiceRepository.getTotalRevenueByRange(startOfMonth, endOfMonth)).orElse(0.0);
        Integer sl = invoiceRepository.getCountRevenueByRange(startOfMonth, endOfMonth);
        Object[] result = new Object[]{price ,sl};
        return result;
    }

    public Object[] getThisYearRevenue() {
        int currentYear = LocalDate.now().getYear();
        Double price = Optional.ofNullable(invoiceRepository.getTotalRevenueByYear(currentYear)).orElse(0.0);
        Integer sl= invoiceRepository.getCountRevenueByYear(currentYear);
        Object[] result = new Object[]{price ,sl};

        return result;
    }

    public Invoice cloneInvoice(Invoice originalInvoice){
        Invoice newInvoice = new Invoice();
        newInvoice.setInvoiceDate(originalInvoice.getInvoiceDate());
        newInvoice.setNetTotal(originalInvoice.getNetTotal());
        newInvoice.setVatTotal(originalInvoice.getVatTotal());
        newInvoice.setGrossTotal(originalInvoice.getGrossTotal());
        newInvoice.setBuyerNoteOnInvoice(originalInvoice.getBuyerNoteOnInvoice());
        newInvoice.setPaymentTime(originalInvoice.getPaymentTime());
        newInvoice.setPaymentType(originalInvoice.getPaymentType());
        newInvoice.setStatus("Draft");
        newInvoice.setPaid(0.0);
        newInvoice.setStatusExit(1);
        newInvoice.setUser(originalInvoice.getUser());
        newInvoice.setVendor(originalInvoice.getVendor());
        newInvoice.setDepartment(originalInvoice.getDepartment());
        newInvoice.setDetails(originalInvoice.getDetails());
        newInvoice.setIsRecurring(originalInvoice.getIsRecurring());
        newInvoice.setRecurringDetails(originalInvoice.getRecurringDetails());
        return createInvoice(newInvoice);
    }
//    public List<Invoice> getInvoices(FilterCriteria filterCriteria){
//        Specification<Invoice> spec = Specification.where(null);
//
//        if(filterCriteria.getStartDate()!=null && filterCriteria.getEndDate()!=null){
//            spec = spec.and(InvoiceSpecification.betweenDates(filterCriteria.getStartDate(), filterCriteria.getEndDate()));
//        }
//        if (filterCriteria.getStatus() != null) {
//            spec = spec.and(InvoiceSpecification.hasStatus(filterCriteria.getStatus()));
//        }
//        if (filterCriteria.getPaymentType() != null) {
//            spec = spec.and(InvoiceSpecification.hasPaymentType(filterCriteria.getPaymentType()));
//        }
//        if (filterCriteria.getKind() != null) {
//            spec = spec.and(InvoiceSpecification.hasKind(filterCriteria.getKind()));
//        }
//        return invoiceRepository.findAll(spec);
//
//    }

    public List<Invoice> getListInvoiceByCondition(String idInvoice, String dateStart, String dateEnd, String status, String paymentType) throws ParseException {
        List<Invoice> invoices = new ArrayList<>();

        try {
            // Parse idInvoice
            Long id = null;
            if (idInvoice != null && !idInvoice.isEmpty()) {
                id = Long.parseLong(idInvoice);
            }

            // Treat empty status as null
            if (status != null && status.isEmpty()) {
                status = null;
            }

            // Treat empty status as null
            if (paymentType != null && paymentType.isEmpty()) {
                paymentType = null;
            }

            // Parse dateStart and dateEnd
            String pattern = "yyyy-MM-dd";
            LocalDate startDate = null;
            LocalDate endDate = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            if (dateStart != null && !dateStart.isEmpty()) {
                startDate = LocalDate.parse(dateStart, formatter);
                System.out.println("startDate after format: "+startDate);
            }
            if (dateEnd != null && !dateEnd.isEmpty()) {
                endDate = LocalDate.parse(dateEnd, formatter);
                System.out.println("endDate after format: "+endDate);
            }

            // Fetch invoices based on conditions
            if(id != null){
                return invoiceRepository.getInvoiceBySequenceNo(id);
            }else if( startDate != null && endDate !=null && status!=null && paymentType!=null){
                return invoiceRepository.getInvoiceByCondition1(startDate, endDate, status, paymentType);
            }else if( startDate != null && endDate !=null && status!=null){
                return invoiceRepository.getInvoiceByCondition2(startDate, endDate, status);
            }else if( startDate != null && endDate !=null && paymentType!=null){
                return invoiceRepository.getInvoiceByCondition3(startDate, endDate, paymentType);
            }else if(status != null && paymentType!=null){
                return invoiceRepository.getInvoiceByCondition4(status, paymentType);
            }else if(startDate != null && endDate !=null){
                return invoiceRepository.getInvoiceByDateRange(startDate, endDate);
            }else if(status != null){
                return invoiceRepository.getInvoiceByStatus(status);
            }else if(paymentType != null){
                return invoiceRepository.getInvoiceByPaymentType(paymentType);
            }else {
                return invoiceRepository.findAll();
            }
//            List<Invoice> invoiceList = invoiceRepository.findInvoicesByConditions(id, startDate, endDate);

        } catch (NumberFormatException e) {
            System.out.println("Invalid invoice ID format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return invoices;
    }
}
