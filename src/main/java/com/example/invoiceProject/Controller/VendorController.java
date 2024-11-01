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
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    // API search vendor by vendor_id, name, phonenumber, email
    @GetMapping("/search")
    public ResponseEntity<List<Vendor>> searchVendor(
            @RequestParam(required = false) Long vendor_id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phonenumber,
            @RequestParam(required = false) String email) {

        // Call the service to perform the search
        List<Vendor> vendors = vendorService.searchVendor(vendor_id, name, phonenumber, email);

        // Return the list of found vendors
        return ResponseEntity.ok(vendors);
    }

    // API lấy Vendor theo vendor_id
    @GetMapping("/{vendor_id}")
    public ResponseEntity<Vendor> getVendorByVendorID(@PathVariable Long vendor_id) {
        Vendor vendor = vendorService.getVendorByVendorID(vendor_id);
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

    @PutMapping("/{vendor_id}")
    public ResponseEntity<String> updateVendor(@RequestBody Map<String, Object> vendorData,
            @PathVariable Long vendor_id) {
        try {
            Vendor existingVendor = vendorService.getVendorByVendorID(vendor_id);
            if (existingVendor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found");
            }
            updateVendorFields(existingVendor, vendorData);
            vendorService.updateVendor(existingVendor, vendor_id);
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

        if (vendorData.containsKey("vendorAddress")) {
            Map<String, Object> addressData = (Map<String, Object>) vendorData.get("vendorAddress");
            VendorAddress address = vendor.getVendorAddress() == null ? new VendorAddress() : vendor.getVendorAddress();
            address.setStreet((String) addressData.get("street"));
            address.setCity((String) addressData.get("city"));
            address.setCountry((String) addressData.get("country"));
            address.setPostCode((String) addressData.get("postCode"));
            vendor.setVendorAddress(address);
        }
    }

    // API xóa Vendor
    @DeleteMapping("/{vendor_id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long vendor_id) {
        try {
            vendorService.deleteVendor(vendor_id);
            return ResponseEntity.ok("Vendor deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
