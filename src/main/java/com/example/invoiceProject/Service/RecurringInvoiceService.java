package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Model.RecurringInvoiceDetails;
import com.example.invoiceProject.Repository.InvoiceRepository;
import com.example.invoiceProject.Repository.RecurringInvoiceDetailRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class RecurringInvoiceService {

    @Autowired
    private RecurringInvoiceDetailRepository recurringInvoiceDetailRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceService invoiceService;



    public void createRecurringInvoiceDetails(RecurringInvoiceDetails recurringInvoiceDetails){
        recurringInvoiceDetailRepository.save(recurringInvoiceDetails);
    }

    public Optional<RecurringInvoiceDetails> getRecurringInvoiceDetailsByInvoiceNo(RecurringInvoiceDetails recurringInvoiceDetails){
        return recurringInvoiceDetailRepository.findByInvoice_InvoiceNo(recurringInvoiceDetails.getInvoice().getInvoiceNo());
    }



//    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    @Scheduled(cron = "1/2 0 * * ?")
    public void processRecurringInvoice(){
        List<RecurringInvoiceDetails> list = recurringInvoiceDetailRepository.findAll();

        if (list.isEmpty()) {
            log.info("No recurring invoices found to process.");
        }
        for(RecurringInvoiceDetails recurringInvoiceDetails : list){
            if(  recurringInvoiceDetails.getNextInvoiceDate() != null && recurringInvoiceDetails.getNextInvoiceDate().equals(LocalDate.now()) ){
                generateNextInvoice(recurringInvoiceDetails);
            }
        }
    }

    @Transactional
    public void generateNextInvoice(RecurringInvoiceDetails recurringDetails) {
        Invoice invoice = recurringDetails.getInvoice();

        //Check if recurring is active and hasn't reached the end date
        if(!invoice.getIsRecurring() || LocalDate.now().isAfter(recurringDetails.getEndDate())){
            throw new IllegalStateException("Recurring invoice is not active or has passed its end date.");
        }else{
            // Create a new invoice using the same details
            Invoice newInvoice = new Invoice();
            newInvoice.setInvoiceDate(LocalDate.now());
            newInvoice.setNetTotal(invoice.getNetTotal());
            newInvoice.setVatTotal(invoice.getVatTotal());
            newInvoice.setGrossTotal(invoice.getGrossTotal());
            newInvoice.setBuyerNoteOnInvoice(invoice.getBuyerNoteOnInvoice());
            newInvoice.setPaymentTime(invoice.getPaymentTime());
            newInvoice.setPaymentType(invoice.getPaymentType());
            newInvoice.setStatus("Draft");
            newInvoice.setUser(invoice.getUser());
            newInvoice.setVendor(invoice.getVendor());
            newInvoice.setDepartment(invoice.getDepartment());
            newInvoice.setDetails(invoice.getDetails());


            // Persist the new invoice
            invoiceService.createInvoice(newInvoice);

            // Update the next recurrence date
            LocalDate nextInvoiceDate = calculateNextDate(recurringDetails);
            recurringDetails.setLastGeneratedDate(LocalDate.now());
            recurringDetails.setNextInvoiceDate(nextInvoiceDate);
        }



        recurringInvoiceDetailRepository.save(recurringDetails);
    }

    private LocalDate calculateNextDate(RecurringInvoiceDetails recurringDetails) {
        LocalDate currentNextDate = recurringDetails.getNextInvoiceDate();
        switch (recurringDetails.getRecurrenceType().toString()) {
            case "DAILY":
                return currentNextDate.plusDays(recurringDetails.getRecurrenceInterval());
            case "WEEKLY":
                return currentNextDate.plusWeeks(recurringDetails.getRecurrenceInterval());
            case "MONTHLY":
                return currentNextDate.plusMonths(recurringDetails.getRecurrenceInterval());
            case "ANNUALLY":
                return currentNextDate.plusYears(recurringDetails.getRecurrenceInterval());
            default:
                throw new IllegalArgumentException("Unsupported recurrence type");
        }
    }
//
}
