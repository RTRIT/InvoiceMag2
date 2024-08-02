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

    public Vendor getVendorByVendorID(Long id){
        return vendorRepository.getVendorByVendorID(id);
    }

    public List<Vendor> getAllVendors(){
        return vendorRepository.getAllVendors();
    }

    public void createVendor(Vendor vendor){
        vendorRepository.save(vendor);
    }

    public void updateVendor(Vendor vendor, Long id){
        vendorRepository.save(vendor);
    }

    public void deleteVendor(Long id){
        vendorRepository.deleteById(id);
    }

}
