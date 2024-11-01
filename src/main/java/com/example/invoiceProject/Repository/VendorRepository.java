package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Model.VendorAddress;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

        boolean existsByEmail(String email);
        boolean existsByPhonenumber(String phonenumber);

        // Get vendor by lastname
        @Query(value = "SELECT * FROM vendor WHERE lastname = :lastname", nativeQuery = true)
        Vendor getVendorByLastName(@Param("lastname") String lastname);

        // Get vendor by vendorID
        @Query(value = "SELECT * FROM vendor WHERE vendor_id = :vendor_id", nativeQuery = true)
        Vendor getVendorById(@Param("vendor_id") Long vendor_id);

        // Get all vendors
        @Query(value = "SELECT * FROM vendor", nativeQuery = true)
        List<Vendor> getAllVendors();

        // Get vendor address by vendorID
        @Query(value = "SELECT * FROM vendor_address WHERE vendor_id = :vendor_id", nativeQuery = true)
        VendorAddress getVendorAddressByVendorId(@Param("vendor_id") Long vendor_id);
        // Create vendor with address
        @Transactional
        @Modifying
        @Query(value = "INSERT INTO vendor (firstname, lastname, tax_identification_number, phonenumber, email, bank_account, bank, logo, address_id) VALUES (:firstname, :lastname, :tax_identification_number, :phonenumber, :email, :bank_account, :bank, :address_id)", nativeQuery = true)
        void createVendor(@Param("firstname") String firstname, @Param("lastname") String lastname,
                        @Param("tax_identification_number") String tax_identification_number,
                        @Param("phonenumber") String phonenumber, @Param("email") String email,
                        @Param("bank_account") String bank_account, @Param("bank") String bank,
//                        @Param("logo") String logo,
                        @Param("address_id") Long address_id);

        // update
        @Transactional
        @Modifying
        @Query(value = "UPDATE vendor SET firstname = :firstname, lastname = :lastname, tax_identification_number = :tax_identification_number, phonenumber = :phonenumber, email = :email, bank_account = :bank_account, bank = :bank, address_id = :address_id WHERE vendor_id = :vendor_id", nativeQuery = true)
        void updateVendor(@Param("firstname") String firstname, @Param("lastname") String lastname,
                        @Param("tax_identification_number") String tax_identification_number,
                        @Param("phonenumber") String phonenumber, @Param("email") String email,
                        @Param("bank_account") String bank_account, @Param("bank") String bank,
//                        @Param("logo") String logo,
                        @Param("address_id") Long address_id, @Param("vendor_id") Long vendor_id);

        // Delete vendor
        @Transactional
        @Modifying
        @Query(value = "DELETE FROM vendor WHERE vendor_id = :vendor_id", nativeQuery = true)
        void deleteVendor(@Param("vendor_id") Long vendor_id);

        //search vendor by vendor_id, name, phonenumber, email
        @Query(value = "SELECT * FROM vendor WHERE vendor_id = :vendor_id OR firstname = :name OR phonenumber = :phonenumber OR email = :email", nativeQuery = true)
        List<Vendor> searchVendor(@Param("vendor_id") Long vendor_id, @Param("name") String name,
                        @Param("phonenumber") String phonenumber, @Param("email") String email);

}