package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VendorRepository extends JpaRepository<Vendor, Long>  {

    @Query(value = "SELECT * FROM vendor WHERE ID = :id", nativeQuery = true)
    Vendor getVendorByVendorID(@Param("id") Long id);

    //Get user by firstname and lastname
    @Query(value = "SELECT * FROM vendor WHERE  Firstname = :firstname AND Lastname = :lastname", nativeQuery = true)
    Vendor getFirstnameAndLastnameByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname);

    //Add vendor
    @Query(value = "INSERT INTO vendor (Firstname, Lastname, TID, Address, Street, City, Country, Postal_code, Phone, Email, Bank_account, Bank) VALUES (:firstname, :lastname, :tax_identification_number, :address, :street, :city, :country, :postcode, :phonenumber, :email, :bankAcount, :bank)", nativeQuery = true)
    void addVendor(@Param("firstname") String firstname, @Param("lastname") String lastname, @Param("tax_identification_number") String tax_identification_number, @Param("address") String address,
                   @Param("street") String street, @Param("city") String city, @Param("country") String country, @Param("postcode") String postcode, @Param("phonenumber") String phonenumber, @Param("email") String email,
                   @Param("bankAcount") String bankAcount, @Param("bank") String bank, @Param("logo") String logo);

    //Update vendor
    @Query(value = "UPDATE vendor SET Firstname = :firstname, Lastname = :lastname, TID = :tax_identification_number, Address = :address, Street = :street, City = :city, Country = :country, Postal_code = :postcode, Phone = :phonenumber, Email = :email, Bank_account = :bankAcount, Bank = :bank, WHERE ID = :id", nativeQuery = true)
    void updateVendor(@Param("firstname") String firstname, @Param("lastname") String lastname, @Param("tax_identification_number") String tax_identification_number, @Param("address") String address,
                      @Param("street") String street, @Param("city") String city, @Param("country") String country, @Param("postcode") String postcode, @Param("phonenumber") String phonenumber, @Param("email") String email,
                      @Param("bankAcount") String bankAcount, @Param("bank") String bank);

    //Delete vendor
    @Query(value = "DELETE FROM vendor WHERE ID = :id", nativeQuery = true)
    void deleteVendor(@Param("id") Long id);



}
