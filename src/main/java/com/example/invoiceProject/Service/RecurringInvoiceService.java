package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Model.RecurringInvoiceDetails;
import com.example.invoiceProject.Repository.InvoiceRepository;
import com.example.invoiceProject.Repository.RecurringInvoiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;


@Service
public class RecurringInvoiceService {

    @Autowired
    private RecurringInvoiceDetailRepository recurringInvoiceDetailRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;



    public RecurringInvoiceDetails createRecurringInvoiceDetails(RecurringInvoiceDetails recurringInvoiceDetails){
        return recurringInvoiceDetailRepository.save(recurringInvoiceDetails);
    }

    public Optional<RecurringInvoiceDetails> getRecurringInvoiceDetailsByInvoiceNo(RecurringInvoiceDetails recurringInvoiceDetails){
        return recurringInvoiceDetailRepository.findByInvoice_InvoiceNo(recurringInvoiceDetails.getInvoice().getInvoiceNo());
    }




//    public RecurringInvoiceDetails createRecurringInvoiceDetails(RecurringInvoiceDetails recurringInvoiceDetails){
//        return recurringInvoiceDetailRepository.save(recurringInvoiceDetails);
//    }
//
//    public Optional<RecurringInvoiceDetails> getRecurringInvoiceDetailsByInvoiceId(UUID invoiceNo){
//        return recurringInvoiceDetailRepository.findByInvoice_InvoiceNo(invoiceNo);
//    }




//    public void generateNextInvoice(RecurringInvoiceDetails recurringDetails) {
//        Invoice invoice = recurringDetails.getInvoice();
//
//        // Create a new invoice using the same details
//        Invoice newInvoice = new Invoice();
//        newInvoice.setInvoiceDate(LocalDate.now());
//        newInvoice.setNetTotal(invoice.getNetTotal());
//        newInvoice.setVatTotal(invoice.getVatTotal());
//        newInvoice.setGrossTotal(invoice.getGrossTotal());
//        newInvoice.setBuyerNoteOnInvoice(invoice.getBuyerNoteOnInvoice());
//        newInvoice.setPaymentTime(invoice.getPaymentTime());
//        newInvoice.setPaymentType(invoice.getPaymentType());
//        newInvoice.setStatus("Draft");
//        newInvoice.setUser(invoice.getUser());
//        newInvoice.setVendor(invoice.getVendor());
//        newInvoice.setDepartment(invoice.getDepartment());
////        newInvoice.setDetails(invoice.getDetails());
//
//
//        // Persist the new invoice
//        invoiceRepository.save(newInvoice);
//
//        // Update the next recurrence date
//        LocalDate nextInvoiceDate = calculateNextDate(recurringDetails);
//        recurringDetails.setLastGeneratedDate(LocalDate.now());
//        recurringDetails.setNextInvoiceDate(nextInvoiceDate);
//
//        recurringInvoiceDetailRepository.save(recurringDetails);
//    }

//    private LocalDate calculateNextDate(RecurringInvoiceDetails recurringDetails) {
//        LocalDate currentNextDate = recurringDetails.getNextInvoiceDate();
//        switch (recurringDetails.getRecurrenceType()) {
//            case "DAILY":
//                return currentNextDate.plusDays(recurringDetails.getRecurrenceInterval());
//            case "WEEKLY":
//                return currentNextDate.plusWeeks(recurringDetails.getRecurrenceInterval());
//            case "MONTHLY":
//                return currentNextDate.plusMonths(recurringDetails.getRecurrenceInterval());
//            case "ANNUALLY":
//                return currentNextDate.plusYears(recurringDetails.getRecurrenceInterval());
//            default:
//                throw new IllegalArgumentException("Unsupported recurrence type");
//        }
//    }

}
