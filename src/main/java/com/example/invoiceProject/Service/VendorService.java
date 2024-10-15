package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Repository.VendorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.invoiceProject.Model.VendorAddress;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    // Get vendor by last name   
    public Vendor getVendorByLastName(String lastname) {
        return vendorRepository.getVendorByLastName(lastname);
    }

    public Vendor getVendorByVendorID(Long vendor_id) {
        return vendorRepository.findById(vendor_id).orElse(null);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.getAllVendors();
    }

    public VendorAddress getVendorAddressByVendorId(Long vendor_id) {
        return vendorRepository.getVendorAddressByVendorId(vendor_id);
    }

    @Transactional
    public void createVendor(Vendor vendor) {
        vendorRepository.save(vendor);
    }

    @Transactional
    public void updateVendor(Vendor vendor, Long vendor_id) {
        if (vendor == null) {
            throw new RuntimeException("Vendor cannot be null");
        }
        
        Vendor existingVendor = vendorRepository.findById(vendor_id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        
        // Copy properties from the input vendor to the existing vendor
        existingVendor.setBank(vendor.getBank());
        existingVendor.setBankAccount(vendor.getBankAccount());
        existingVendor.setEmail(vendor.getEmail());
        existingVendor.setFirstname(vendor.getFirstname());
        existingVendor.setLastname(vendor.getLastname());
        existingVendor.setLogo(vendor.getLogo());
        existingVendor.setPhonenumber(vendor.getPhonenumber());
        existingVendor.setTaxIdentificationNumber(vendor.getTaxIdentificationNumber());
        existingVendor.setVendorAddress(vendor.getVendorAddress());
        
        vendorRepository.save(existingVendor);
    }

    @Transactional
    public void deleteVendor(Long vendor_id) {
        vendorRepository.deleteById(vendor_id);
    }
}