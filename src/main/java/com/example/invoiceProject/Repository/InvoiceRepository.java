package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Invoice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    @Query("SELECT MAX(i.sequenceNo) FROM Invoice i")
    Long findMaxSequenceNo();

//    @Query("SELECT i FROM Invoice i WHERE")
//    List<Invoice> findInvoicesByConditions();

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
    @Query(value="SELECT *FROM Invoice WHERE invoice_no= :invoiceNo", nativeQuery = true)
    Invoice getInvoiceByInvoiceNo(@Param("invoiceNo") UUID invoiceNo);

    //----------Get invoice by condition------------
    //Get Invoice by SequnceNo
    @Query(value="SELECT i FROM Invoice i WHERE i.sequenceNo= :sequenceNo")
    List<Invoice> getInvoiceBySequenceNo(@Param("sequenceNo") Long sequenceNo);

    //Get Invoice by Date range
    @Query("SELECT i FROM Invoice i WHERE i.invoiceDate >= :startDate AND i.invoiceDate <= :endDate")
    List<Invoice> getInvoiceByDateRange(@Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);

    //Get invoice by invoice status
    @Query("SELECT i FROM Invoice i WHERE i.status = :status")
    List<Invoice> getInvoiceByStatus(@Param("status") String status);

    //Get invoice by invoice payment type
    @Query("SELECT i FROM Invoice i WHERE i.paymentType = :paymentType")
    List<Invoice> getInvoiceByPaymentType(@Param("paymentType") String paymentType);


    //Get Invoice by Condition
    @Query(value="SELECT i FROM Invoice i WHERE i.status = :status AND i.paymentType = :paymentType AND i.invoiceDate >= :startDate AND i.invoiceDate <= :endDate ")
    List<Invoice> getInvoiceByCondition1(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate,
                                        @Param("status") String status,
                                         @Param("paymentType") String paymentType);


    @Query(value="SELECT i FROM Invoice i WHERE i.status = :status  AND i.invoiceDate >= :startDate AND i.invoiceDate <= :endDate ")
    List<Invoice> getInvoiceByCondition2(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate,
                                         @Param("status") String status);


    @Query(value="SELECT i FROM Invoice i WHERE i.paymentType = :paymentType AND i.invoiceDate >= :startDate AND i.invoiceDate <= :endDate ")
    List<Invoice> getInvoiceByCondition3(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate,
                                         @Param("paymentType") String paymentType);

    @Query(value="SELECT i FROM Invoice i WHERE i.paymentType = :paymentType AND i.status = :status ")
    List<Invoice> getInvoiceByCondition4(@Param("status") String status,
                                         @Param("paymentType") String paymentType);


    //Get All Invoice
    @Query(value="SELECT *FROM Invoice",nativeQuery = true)
    List<Invoice> getAllInvoices();

    // Update Invoice by InvoiceNo
    @Transactional
    @Modifying
    @Query(value = "UPDATE Invoice SET invoice_date = :invoiceDate, sequence_no = :sequenceNo, buyer_note_on_invoice = :buyerNoteOnInvoice, " +
                    "unit_price = :unitPrice, payment_method = :paymentMethod, amount = :amount, vat = :vat WHERE invoice_no = :invoiceNo", nativeQuery = true)
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


    @Query("SELECT SUM(i.grossTotal) FROM Invoice i WHERE i.invoiceDate = :date AND i.status='Paid'")
    Double getTotalRevenueByDay(LocalDate date);
    @Query("SELECT COUNT(i) FROM Invoice i WHERE i.invoiceDate = :date AND i.status='Paid'")
    Integer getCountRevenueByDay(LocalDate date);

    @Query("SELECT SUM(i.grossTotal) FROM Invoice i WHERE i.invoiceDate >= :startDate AND i.invoiceDate <= :endDate AND i.status='Paid'")
    Double getTotalRevenueByRange(LocalDate startDate, LocalDate endDate);
    @Query("SELECT COUNT(i) FROM Invoice i WHERE i.invoiceDate >= :startDate AND i.invoiceDate <= :endDate AND i.status='Paid'")
    Integer getCountRevenueByRange(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(i.grossTotal) FROM Invoice i WHERE MONTH(i.invoiceDate) = :month AND i.status='Paid'")
    Double getTotalRevenueByMonth(int month);
    @Query("SELECT COUNT(i) FROM Invoice i WHERE MONTH(i.invoiceDate) = :month AND i.status='Paid'")
    Integer getCountRevenueByMonth(int month);

    @Query("SELECT SUM(i.grossTotal) FROM Invoice i WHERE YEAR(i.invoiceDate) = :year AND i.status='Paid'")
    Double getTotalRevenueByYear(int year);
    @Query("SELECT COUNT(i) FROM Invoice i WHERE YEAR(i.invoiceDate) = :year AND i.status='Paid'")
    Integer getCountRevenueByYear(int year);

    //Delete Invoice by InvoiceNo
    @Transactional
    @Modifying
    @Query(value="DELETE FROM Invoice WHERE invoice_no= :invoiceNo",nativeQuery = true)
    void deleteInvoiceByInvoiceNo(@Param("invoiceNo")UUID invoiceNo);
}
