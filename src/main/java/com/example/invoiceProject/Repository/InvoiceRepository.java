package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Invoice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    //Create Invoice
    @Transactional
    @Modifying
    @Query(value= "Insert into Invoice(invoice_date, sequence_no, buyer_note_on_invoice, unit_price, payment_method, amount, vat, status, paid) values (:invoiceDate, :sequenceNo, :buyerNoteOnInvoice,:unitPrice,:paymentMethod,:amount,:vat,:status,:paid)",nativeQuery = true)
    void createInvoice(@Param("invoiceDate") LocalDate invoiceDate,
                       @Param("sequenceNo") Integer sequenceNo,
                       @Param("buyerNoteOnInvoice") String buyerNoteOnInvoice,
                       @Param("unitPrice") Double unitPrice,
                       @Param("paymentMethod") String paymentMethod,
                       @Param("amount") Double amount,
                       @Param("vat") Double vat,
                       @Param("status") String status,
                       @Param("paid") Double paid);

    //Get Invoice by InvoiceNo
    @Query(value="SELECT *FROM Invoice WHERE invoice_no= :invoiceNo", nativeQuery = true)
    Invoice getInvoiceByInvoiceNo(@Param("invoiceNo")Long invoiceNo);

    //Get All Invoice
    @Query(value="SELECT *FROM Invoice",nativeQuery = true)
    List<Invoice> getAllInvoices();

    // Update Invoice by InvoiceNo
    @Transactional
    @Modifying
    @Query(value = "UPDATE Invoice SET invoice_date = :invoiceDate, sequence_no = :sequenceNo, buyer_note_on_invoice = :buyerNoteOnInvoice, " +
                    "unit_price = :unitPrice, payment_method = :paymentMethod, amount = :amount, vat = :vat, status=:status, paid=:paid) WHERE invoice_no = :invoiceNo", nativeQuery = true)
    void updateInvoiceByInvoiceNo(
            @Param("invoiceDate") LocalDate invoiceDate,
            @Param("sequenceNo") Integer sequenceNo,
            @Param("buyerNoteOnInvoice") String buyerNoteOnInvoice,
            @Param("unitPrice") Double unitPrice,
            @Param("paymentMethod") String paymentMethod,
            @Param("amount") Double amount,
            @Param("vat") Double vat,
            @Param("invoiceNo") Long invoiceNo,
            @Param("status")String status,
            @Param("paid")Double paid
    );


    //Delete Invoice by InvoiceNo
    @Transactional
    @Modifying
    @Query(value="DELETE FROM Invoice WHERE invoice_no= :invoiceNo",nativeQuery = true)
    void deleteInvoiceByInvoiceNo(@Param("invoiceNo")Long invoiceNo);
}
