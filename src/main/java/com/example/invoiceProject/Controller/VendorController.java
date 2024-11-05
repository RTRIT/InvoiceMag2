package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.requests.VendorCreationRequest;
import com.example.invoiceProject.DTO.response.VendorResponse;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Model.VendorAddress;
import com.example.invoiceProject.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    // API search vendor by vendorUuid, name, phonenumber, email
    @GetMapping("/search")
    public ResponseEntity<List<Vendor>> searchVendor(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phonenumber,
            @RequestParam(required = false) String email) {

        // Call the service to perform the search
        List<Vendor> vendors = vendorService.searchVendor(name, phonenumber, email);

        // Return the list of found vendors
        return ResponseEntity.ok(vendors);
    }

    // API lấy Vendor theo vendorUuid
    @GetMapping("/{vendorid}")
    public ResponseEntity<Vendor> getVendorByVendorID(@PathVariable UUID vendorid) {
        Vendor vendor = vendorService.getVendorByVendorID(vendorid);
        return ResponseEntity.ok(vendor);
    }

    // API lấy tất cả Vendor
    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    // API tạo Vendor mới
    @PostMapping
    public ResponseEntity<VendorResponse> createVendor(@RequestBody VendorCreationRequest request) {
        VendorResponse vendorResponse = vendorService.createVendor(request);
        return ResponseEntity.ok(vendorResponse);
    }

    @PutMapping("/{vendorid}")
    public ResponseEntity<String> updateVendor(@RequestBody Map<String, Object> vendorData,
            @PathVariable UUID vendorid) {
        try {
            Vendor existingVendor = vendorService.getVendorByVendorID(vendorid);
            if (existingVendor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found");
            }
            updateVendorFields(existingVendor, vendorData);
            vendorService.updateVendor(existingVendor, vendorid);
            return ResponseEntity.ok("Vendor updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // Phương thức hỗ trợ cho updateVendor
    private void updateVendorFields(Vendor vendor, Map<String, Object> vendorData) {
        vendor.setFirstname((String) vendorData.get("firstname"));
        vendor.setLastname((String) vendorData.get("lastname"));
        vendor.setTaxIdentificationNumber((String) vendorData.get("taxIdentificationNumber"));
        vendor.setPhonenumber((String) vendorData.get("phonenumber"));
        vendor.setEmail((String) vendorData.get("email"));
        vendor.setBankAccount((String) vendorData.get("bankAccount"));
        vendor.setBank((String) vendorData.get("bank"));
        vendor.setNote((String) vendorData.get("note"));

        if (vendorData.containsKey("vendorAddress")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> addressData = (Map<String, Object>) vendorData.get("vendorAddress");
            VendorAddress vendorAddress = vendor.getVendorAddress();
            if (vendorAddress == null) {
                vendorAddress = new VendorAddress();
            }
            vendorAddress.setStreet((String) addressData.get("street"));
            vendorAddress.setCity((String) addressData.get("city"));
            vendorAddress.setCountry((String) addressData.get("country"));
            vendorAddress.setPostCode((String) addressData.get("postCode"));
            vendor.setVendorAddress(vendorAddress);
        }
        
    }

    // API xóa Vendor
    @DeleteMapping("/{vendorid}")
    public ResponseEntity<String> deleteVendor(@PathVariable UUID vendorid) {
        try {
            vendorService.deleteVendor(vendorid);
            return ResponseEntity.ok("Vendor deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
