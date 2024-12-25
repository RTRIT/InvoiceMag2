package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Model.PaymentType;
import com.example.invoiceProject.Model.Vendor;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

        boolean existsByEmail(String email);

        Optional<Vendor> findByEmail(String email);

        boolean existsByPhonenumber(String phonenumber);

        List<Vendor> findAllByStatus(int status);

        // findbyvendorid
        Optional<Vendor> findByVendorid(UUID vendorid);

        @Query("SELECT v FROM Vendor v WHERE " +
                        "LOWER(v.firstname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                        "LOWER(v.lastname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                        "LOWER(v.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                        "v.phonenumber LIKE CONCAT('%', :keyword, '%')")
        List<Vendor> findByKeyword(@Param("keyword") String keyword);

        // find list invoice by email vendor
        @Query("SELECT i FROM Invoice i WHERE i.vendor.email = :email")
        List<Invoice> findInvoicesByVendorEmail(@Param("email") String email);

        // Get vendor by vendorID
        @Query(value = "SELECT * FROM vendor WHERE vendorid = :vendorid", nativeQuery = true)
        Vendor getVendorByVendorId(@Param("vendorid") UUID vendorid);

        // Delete vendor
        @Transactional
        @Modifying
        @Query(value = "UPDATE vendor SET status = :status WHERE vendorid = :vendorid", nativeQuery = true)
        void updateVendorStatus(@Param("vendorid") UUID vendorid, @Param("status") int status);

}