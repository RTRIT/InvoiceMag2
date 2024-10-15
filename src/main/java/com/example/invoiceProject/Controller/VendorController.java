package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Model.VendorAddress;
import com.example.invoiceProject.Service.VendorService;
import com.example.invoiceProject.Repository.VendorAddressRepository;
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

    @Autowired
    private VendorAddressRepository vendorAddressRepository;

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
    public ResponseEntity<?> createVendor(@RequestBody Map<String, Object> vendorData) {
        try {
            Vendor vendor = new Vendor();
            vendor.setFirstname((String) vendorData.get("firstname"));
            vendor.setLastname((String) vendorData.get("lastname"));
            vendor.setTaxIdentificationNumber((String) vendorData.get("taxIdentificationNumber"));
            vendor.setPhonenumber((String) vendorData.get("phonenumber"));
            vendor.setEmail((String) vendorData.get("email"));
            vendor.setBankAccount((String) vendorData.get("bankAccount"));
            vendor.setBank((String) vendorData.get("bank"));
            vendor.setLogo((String) vendorData.get("logo"));

            // Tạo và lưu địa chỉ nếu có
            if (vendorData.containsKey("vendorAddress")) {
                Map<String, Object> addressData = (Map<String, Object>) vendorData.get("vendorAddress");
                VendorAddress vendorAddress = new VendorAddress();
                vendorAddress.setNumberAddress((String) addressData.get("numberAddress"));
                vendorAddress.setStreet((String) addressData.get("street"));
                vendorAddress.setCity((String) addressData.get("city"));
                vendorAddress.setCountry((String) addressData.get("country"));

                // Xử lý postCode
                Object postCodeObject = addressData.get("postCode");
                Long postCode = parsePostCode(postCodeObject);
                vendorAddress.setPostCode(postCode);

                // Lưu địa chỉ vào cơ sở dữ liệu
                VendorAddress savedAddress = vendorAddressRepository.save(vendorAddress);
                vendor.setVendorAddress(savedAddress);
            }

            // Lưu vendor vào cơ sở dữ liệu
            vendorService.createVendor(vendor);

            return ResponseEntity.ok("Vendor created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // API cập nhật Vendor
    @PutMapping("/{vendor_id}")
    public ResponseEntity<String> updateVendor(@RequestBody Map<String, Object> vendorData, @PathVariable Long vendor_id) {
        try {
            // Tìm vendor theo ID
            Vendor existingVendor = vendorService.getVendorByVendorID(vendor_id);
            if (existingVendor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found");
            }

            // Cập nhật thông tin vendor
            existingVendor.setFirstname((String) vendorData.get("firstname"));
            existingVendor.setLastname((String) vendorData.get("lastname"));
            existingVendor.setTaxIdentificationNumber((String) vendorData.get("taxIdentificationNumber"));
            existingVendor.setPhonenumber((String) vendorData.get("phonenumber"));
            existingVendor.setEmail((String) vendorData.get("email"));
            existingVendor.setBankAccount((String) vendorData.get("bankAccount"));
            existingVendor.setBank((String) vendorData.get("bank"));
            existingVendor.setLogo((String) vendorData.get("logo"));

            // Cập nhật địa chỉ nếu có
            if (vendorData.containsKey("vendorAddress")) {
                Map<String, Object> addressData = (Map<String, Object>) vendorData.get("vendorAddress");
                VendorAddress existingAddress = existingVendor.getVendorAddress();

                if (existingAddress == null) {
                    // Nếu vendor chưa có địa chỉ, tạo mới
                    existingAddress = new VendorAddress();
                    existingVendor.setVendorAddress(existingAddress);
                }

                existingAddress.setNumberAddress((String) addressData.get("numberAddress"));
                existingAddress.setStreet((String) addressData.get("street"));
                existingAddress.setCity((String) addressData.get("city"));
                existingAddress.setCountry((String) addressData.get("country"));

                // Xử lý postCode
                Object postCodeObject = addressData.get("postCode");
                Long postCode = parsePostCode(postCodeObject);
                existingAddress.setPostCode(postCode);

                // Lưu địa chỉ vào cơ sở dữ liệu
                vendorAddressRepository.save(existingAddress);
            }

            // Lưu vendor vào cơ sở dữ liệu
            vendorService.updateVendor(existingVendor, vendor_id);

            return ResponseEntity.ok("Vendor updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
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

    // API lấy địa chỉ của Vendor theo vendor_id
    @GetMapping("/{vendor_id}/address")
    public ResponseEntity<VendorAddress> getVendorAddressByVendorID(@PathVariable Long vendor_id) {
        VendorAddress vendorAddress = vendorService.getVendorAddressByVendorId(vendor_id);
        if (vendorAddress != null) {
            return ResponseEntity.ok(vendorAddress);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Phương thức chuyển đổi giá trị postCode
    private Long parsePostCode(Object postCodeObject) {
        if (postCodeObject instanceof Integer) {
            return ((Integer) postCodeObject).longValue();
        } else if (postCodeObject instanceof Long) {
            return (Long) postCodeObject;
        } else {
            throw new IllegalArgumentException("Invalid postCode format");
        }
    }
}
