package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.invoiceProject.Model.*;
import com.example.invoiceProject.Repository.*;


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/vendors")

public class VendorController {
    @Autowired
    private VendorService vendorService;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @GetMapping("/{vendor_id}")
    public ResponseEntity<Vendor> getVendorByVendorID(@PathVariable Long vendor_id) {
        Vendor vendor = vendorService.getVendorByVendorID(vendor_id);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    @PostMapping
    public ResponseEntity<?> createVendor(@RequestBody VendorDTO vendorDTO) {
        try {
            // Tìm kiếm đối tượng Type dựa trên typeID
            Type type = typeRepository.findById(vendorDTO.getTypeID())
                                        .orElseThrow(() -> new RuntimeException("Type not found with ID: " + vendorDTO.getTypeID()));
            
            // Tạo đối tượng Vendor và ánh xạ các giá trị từ vendorDTO
            Vendor vendor = new Vendor();
            vendor.setFirstname(vendorDTO.getFirstname());
            vendor.setLastname(vendorDTO.getLastname());
            vendor.setTaxIdentificationNumber(vendorDTO.getTaxIdentificationNumber());
            vendor.setAddress(vendorDTO.getAddress());
            vendor.setStreet(vendorDTO.getStreet());
            vendor.setCity(vendorDTO.getCity());
            vendor.setCountry(vendorDTO.getCountry());
            vendor.setPostcode(vendorDTO.getPostcode());
            vendor.setPhonenumber(vendorDTO.getPhonenumber());
            vendor.setEmail(vendorDTO.getEmail());
            vendor.setBankAccount(vendorDTO.getBankAccount());
            vendor.setBank(vendorDTO.getBank());
            vendor.setLogo(vendorDTO.getLogo());
            
            // Gán đối tượng Type vào Vendor
            vendor.setType(type);

            // Lưu Vendor vào cơ sở dữ liệu
            vendorRepository.save(vendor);

            return ResponseEntity.ok(vendor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PutMapping("/{vendor_id}")
    public ResponseEntity<String> updateVendor(@RequestBody Vendor vendor, @PathVariable Long vendor_id) {
        vendorService.updateVendor(vendor, vendor_id);
        return ResponseEntity.ok("Vendor updated successfully");
    }

    @DeleteMapping("/{vendor_id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long vendor_id) {
        vendorService.deleteVendor(vendor_id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}