package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.DetailInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailInvoiceRepository extends JpaRepository<DetailInvoice, Long> {

    // Find DetailInvoice by product code
    Optional<DetailInvoice> findById(Long id);

//    // Find all DetailInvoices by a specific invoice_id
//    List<DetailInvoice> findByInvoice_InvoiceNo(Long invoiceNo);


    // Custom delete operation by product code
    void deleteById(Long id);
}
