package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Invoice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    //Create Invoice
    @Transactional
    @Modifying
    @Query(value= "Insert into Invoice(invoice_date, sequence_no, buyer_note_on_invoice, unit_price, payment_method, amount, vat) values (:invoiceDate, :sequenceNo, :buyerNoteOnInvoice,:unitPrice,:paymentMethod,:amount,:vat)",nativeQuery = true)
    void createInvoice(@Param("invoiceDate") LocalDate invoiceDate,
                       @Param("sequenceNo") Integer sequenceNo,
                       @Param("buyerNoteOnInvoice") String buyerNoteOnInvoice,
                       @Param("unitPrice") Double unitPrice,
                       @Param("paymentMethod") String paymentMethod,
                       @Param("amount") Double amount,
                       @Param("vat") Double vat);

    //Get Invoice by InvoiceNo
    @Query(value = "SELECT * FROM Invoice WHERE invoice_no = :invoiceNo", nativeQuery = true)
    Invoice getInvoiceByInvoiceNo(@Param("invoiceNo") UUID invoiceNo);

    //Get All Invoice 
    @Query(value = "SELECT * FROM Invoice", nativeQuery = true)
    List<Invoice> getAllInvoices();

    // Update Invoice by InvoiceNo  
    @Transactional
    @Modifying
    @Query(value = "UPDATE Invoice SET invoice_date = :invoiceDate, sequence_no = :sequenceNo, buyer_note_on_invoice = :buyerNoteOnInvoice" +
                "unit_price = :unitPrice, payment_method = :paymentMethod, amount = :amount, vat = :vat WHERE invoice_no = :invoiceNo", 
        nativeQuery = true)
    void updateInvoiceByInvoiceNo(
            @Param("invoiceDate") LocalDate invoiceDate,
            @Param("sequenceNo") Integer sequenceNo,
            @Param("buyerNoteOnInvoice") String buyerNoteOnInvoice,
            @Param("unitPrice") Double unitPrice,
            @Param("paymentMethod") String paymentMethod,
            @Param("amount") Double amount,
            @Param("vat") Double vat,
            @Param("invoiceNo") UUID invoiceNo
    );

    //Delete Invoice by InvoiceNo
    @Transactional
    @Modifying
    @Query(value="DELETE FROM Invoice WHERE invoice_no= :invoiceNo",nativeQuery = true)
    void deleteInvoiceByInvoiceNo(@Param("invoiceNo")UUID invoiceNo);

}
