package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.invoiceProject.Model.*;
import com.example.invoiceProject.Repository.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createVendor(@RequestBody Map<String, Object> vendorData) {
        try {
            // Tìm kiếm đối tượng Type dựa trên typeID từ dữ liệu đầu vào
            Long typeId = Long.parseLong(vendorData.get("typeID").toString());
            Type type = typeRepository.findById(typeId)
                                       .orElseThrow(() -> new RuntimeException("Type not found with ID: " + typeId));
            
            // Tạo đối tượng Vendor và ánh xạ các giá trị
            Vendor vendor = new Vendor();
            vendor.setFirstname(vendorData.get("firstname").toString());
            vendor.setLastname(vendorData.get("lastname").toString());
            vendor.setTaxIdentificationNumber(vendorData.get("taxIdentificationNumber").toString());
            vendor.setAddress(vendorData.get("address").toString());
            vendor.setStreet(vendorData.get("street").toString());
            vendor.setCity(vendorData.get("city").toString());
            vendor.setCountry(vendorData.get("country").toString());
            vendor.setPostcode(vendorData.get("postcode").toString());
            vendor.setPhonenumber(vendorData.get("phonenumber").toString());
            vendor.setEmail(vendorData.get("email").toString());
            vendor.setBankAccount(vendorData.get("bankAccount").toString());
            vendor.setBank(vendorData.get("bank").toString());
            vendor.setLogo(vendorData.get("logo").toString());
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