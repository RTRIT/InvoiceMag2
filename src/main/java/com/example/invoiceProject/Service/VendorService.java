package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Repository.VendorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor getVendorByVendorID(Long vendor_id) {
        return vendorRepository.findById(vendor_id).orElse(null);
    }

    public List<Vendor> getAllVendors(){
        return vendorRepository.getAllVendors();
    }

    public void createVendor(Vendor vendor){
        vendorRepository.save(vendor);
    }

    public void updateVendor(Vendor vendor, Long vendor_id) {
        Vendor existingVendor = vendorRepository.findById(vendor_id).orElseThrow(() -> new RuntimeException("Vendor not found"));
        // Copy properties from the input vendor to the existing vendor
        existingVendor.setAddress(vendor.getAddress());
        existingVendor.setBank(vendor.getBank());
        existingVendor.setBankAccount(vendor.getBankAccount());
        existingVendor.setCity(vendor.getCity());
        existingVendor.setCountry(vendor.getCountry());
        existingVendor.setEmail(vendor.getEmail());
        existingVendor.setFirstname(vendor.getFirstname());
        existingVendor.setLastname(vendor.getLastname());
        existingVendor.setLogo(vendor.getLogo());
        existingVendor.setPhonenumber(vendor.getPhonenumber());
        existingVendor.setPostcode(vendor.getPostcode());
        existingVendor.setStreet(vendor.getStreet());
        existingVendor.setTaxIdentificationNumber(vendor.getTaxIdentificationNumber());
        existingVendor.setType(vendor.getType());
        existingVendor.setUser(vendor.getUser());
        vendorRepository.save(existingVendor);
    }

    public void deleteVendor(Long vendor_id){
        vendorRepository.deleteById(vendor_id);
    }

}
