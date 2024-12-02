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

        //list vendor by lastname
        List<Vendor> findByLastname(String lastname);

        //list vendor by firstname
        List<Vendor> findByFirstname(String firstname);

        // Tìm theo số điện thoại chính xác
        List<Vendor> findByPhonenumber(String phonenumber);

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

        // Create vendor with address
        @Transactional
        @Modifying
        @Query(value = "INSERT INTO vendor (firstname, lastname, tax_identification_number, phonenumber, email, bank_account, bank, logo, addr) VALUES (:firstname, :lastname, :tax_identification_number, :phonenumber, :email, :bank_account, :bank, :addr)", nativeQuery = true)
        void createVendor(@Param("firstname") String firstname, @Param("lastname") String lastname,
                          @Param("tax_identification_number") String tax_identification_number,
                          @Param("phonenumber") String phonenumber, @Param("email") String email,
                          @Param("bank_account") String bank_account, @Param("bank") String bank,
                          // @Param("logo") String logo,
                          @Param("addr") Long addr);

        // update
        @Transactional
        @Modifying
        @Query(value = "UPDATE vendor SET firstname = :firstname, lastname = :lastname, tax_identification_number = :tax_identification_number, phonenumber = :phonenumber, email = :email, bank_account = :bank_account, bank = :bank, addr = :addr WHERE vendorid = :vendorid", nativeQuery = true)
        void updateVendor(@Param("firstname") String firstname, @Param("lastname") String lastname,
                          @Param("tax_identification_number") String tax_identification_number,
                          @Param("phonenumber") String phonenumber, @Param("email") String email,
                          @Param("bank_account") String bank_account, @Param("bank") String bank,
                          // @Param("logo") String logo,
                          @Param("addr") Long addr, @Param("vendorid") UUID vendorid);

        // Delete vendor
        @Transactional
        @Modifying
        @Query(value = "DELETE FROM vendor WHERE vendorid = :vendorid", nativeQuery = true)
        void deleteById(@Param("vendorid") UUID vendorid);

        List<Vendor> findByVendoridAndEmail(UUID vendorid, String email);

}