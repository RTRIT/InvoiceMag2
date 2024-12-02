package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Vendor;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

        boolean existsByEmail(String email);

        boolean existsByPhonenumber(String phonenumber);

        //findbyvendorid
        Optional<Vendor> findByVendorid(UUID vendorid);
        Optional<Vendor> findByEmail(String email);
        List<Vendor> findAll();

        // Get vendor by lastname
        @Query(value = "SELECT * FROM vendor WHERE lastname = :lastname", nativeQuery = true)
        Vendor getVendorByLastName(@Param("lastname") String lastname);

        // Get vendor by vendorID
        @Query(value = "SELECT * FROM vendor WHERE vendorid = :vendorid", nativeQuery = true)
        Vendor getVendorByVendorId(@Param("vendorid") UUID vendorid);

        // Get all vendors
//        @Query(value = "SELECT * FROM vendor", nativeQuery = true)
//        List<Vendor> getAllVendors();

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

        // search vendor by vendor_id, name, phonenumber, email
        @Query(value = "SELECT * FROM vendor WHERE firstname = :name OR lastname = :name OR phonenumber = :phonenumber OR email = :email", nativeQuery = true)
        List<Vendor> searchVendor(@Param("name") String name, @Param("phonenumber") String phonenumber,
                        @Param("email") String email);

}