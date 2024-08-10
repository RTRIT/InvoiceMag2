package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Vendor;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    // Get vendor by vendorID
    @Query(value = "SELECT * FROM vendor WHERE vendor_id = :vendor_id", nativeQuery = true)
    Vendor getVendorById(@Param("vendor_id") Long vendor_id);

    // Get all vendors
    @Query(value = "SELECT * FROM vendor", nativeQuery = true)
    List<Vendor> getAllVendors();

    // Create vendor
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO vendor (firstname, lastname, TID, Address, street, city, Country, postal_code, phone, email, bank_account, bank, type) VALUES (:firstname, :lastname, :tax_identification_number, :address, :street, :city, :country, :postcode, :phonenumber, :email, :bankAcount, :bank, :type)", nativeQuery = true)
    void createVendor(@Param("firstname") String firstname, @Param("lastname") String lastname, @Param("tax_identification_number") String tax_identification_number, @Param("address") String address,
                      @Param("street") String street, @Param("city") String city, @Param("country") String country, @Param("postcode") String postcode, @Param("phonenumber") String phonenumber, @Param("email") String email,
                      @Param("bankAcount") String bankAcount, @Param("bank") String bank, @Param("type") Long type);

    // Update vendor by vendorID
    @Transactional
    @Modifying
    @Query(value = "UPDATE vendor SET firstname = :firstname, lastname = :lastname, TID = :tax_identification_number, Address = :address, street = :street, city = :city, Country = :country, postal_code = :postcode, phone = :phonenumber, email = :email, bank_account = :bankAcount, bank = :bank, type = :type WHERE vendor_id = :vendor_id", nativeQuery = true)
    void updateVendor(@Param("vendor_id") Long vendor_id, @Param("firstname") String firstname, @Param("lastname") String lastname, @Param("tax_identification_number") String tax_identification_number, @Param("address") String address,
                      @Param("street") String street, @Param("city") String city, @Param("country") String country, @Param("postcode") String postcode, @Param("phonenumber") String phonenumber, @Param("email") String email,
                      @Param("bankAcount") String bankAcount, @Param("bank") String bank, @Param("type") Long type);

    // Delete vendor
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM vendor WHERE vendor_id = :vendor_id", nativeQuery = true)
    void deleteVendor(@Param("vendor_id") Long vendor_id);
}
