package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")

public class VendorController {
    @Autowired
    VendorService vendorService;

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorByVendorID(@PathVariable Long id) {
        Vendor vendor = vendorService.getVendorByVendorID(id);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    @PostMapping
    public ResponseEntity<String> createVendor(@RequestBody Vendor vendor) {
        vendorService.createVendor(vendor);
        return ResponseEntity.ok("Vendor created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVendor(@RequestBody Vendor vendor, @PathVariable Long id) {
        vendorService.updateVendor(vendor, id);
        return ResponseEntity.ok("Vendor updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}