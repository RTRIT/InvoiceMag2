package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.requests.VendorCreationRequest;
import com.example.invoiceProject.DTO.response.VendorResponse;
import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/vendors")
public class
VendorController {

    @Autowired
    private VendorService vendorService;

    //search list vendor
    @GetMapping("/search")
    public ResponseEntity<List<Vendor>> searchVendor(@RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String email,
                                                     @RequestParam(required = false) String phonenumber) {
        List<Vendor> vendors = vendorService.searchVendor(name, email, phonenumber);
        return ResponseEntity.ok(vendors);
    }
    
    //create vendor
    @PostMapping
    public ResponseEntity<VendorResponse> createVendor(@RequestBody VendorCreationRequest request) {
        VendorResponse vendorResponse = vendorService.createVendor(request);
        return ResponseEntity.ok(vendorResponse);
    }

    // Lấy Vendor theo ID
    @GetMapping("/{vendorid}")
    public ResponseEntity<VendorResponse> getVendorByVendorID(@PathVariable UUID vendorid) {
        VendorResponse vendorResponse = vendorService.getVendorByVendorID(vendorid);
        return ResponseEntity.ok(vendorResponse);
    }

    // Lấy tất cả Vendor
    @GetMapping
    public ResponseEntity<List<VendorResponse>> getAllVendors() {
        List<VendorResponse> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    // Cập nhật Vendor
    @PutMapping("/{vendorid}")
    public ResponseEntity<VendorResponse> updateVendor(@PathVariable UUID vendorid, @RequestBody VendorCreationRequest request) {
        VendorResponse vendorResponse = vendorService.updateVendor(vendorid, request);
        return ResponseEntity.ok(vendorResponse);
    }

    // Xóa Vendor
    @DeleteMapping("/{vendorid}")
    public ResponseEntity<String> deleteVendor(@PathVariable UUID vendorid) {
        vendorService.deleteVendor(vendorid);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}
