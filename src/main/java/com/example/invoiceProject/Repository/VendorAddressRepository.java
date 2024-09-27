package com.example.invoiceProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.invoiceProject.Model.VendorAddress;

@Repository
public interface VendorAddressRepository extends JpaRepository<VendorAddress, Long> {

}
