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
        if (vendor_id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Xử lý lỗi nếu vendor_id là null
        }
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
//            vendor.setLogo((String) vendorData.get("logo"));

            // Tạo và lưu địa chỉ nếu có
            if (vendorData.containsKey("vendorAddress")) {
                Map<String, Object> addressData = (Map<String, Object>) vendorData.get("vendorAddress");
                VendorAddress vendorAddress = new VendorAddress();
                vendorAddress.setStreet((String) addressData.get("street"));
                vendorAddress.setCity((String) addressData.get("city"));
                vendorAddress.setCountry((String) addressData.get("country"));
                vendorAddress.setPostCode((String) addressData.get("postCode"));

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
            if (vendorData.containsKey("firstname")) {
                existingVendor.setFirstname((String) vendorData.get("firstname"));
            }
            if (vendorData.containsKey("lastname")) {
                existingVendor.setLastname((String) vendorData.get("lastname"));
            }
            if (vendorData.containsKey("taxIdentificationNumber")) {
                existingVendor.setTaxIdentificationNumber((String) vendorData.get("taxIdentificationNumber"));
            }
            if (vendorData.containsKey("phonenumber")) {
                existingVendor.setPhonenumber((String) vendorData.get("phonenumber"));
            }
            if (vendorData.containsKey("email")) {
                existingVendor.setEmail((String) vendorData.get("email"));
            }
            if (vendorData.containsKey("bankAccount")) {
                existingVendor.setBankAccount((String) vendorData.get("bankAccount"));
            }
            if (vendorData.containsKey("bank")) {
                existingVendor.setBank((String) vendorData.get("bank"));
            }

            // Cập nhật địa chỉ nếu có
            if (vendorData.containsKey("vendorAddress")) {
                Map<String, Object> addressData = (Map<String, Object>) vendorData.get("vendorAddress");
                VendorAddress existingAddress = existingVendor.getVendorAddress();

                if (existingAddress == null) {
                    // Nếu vendor chưa có địa chỉ, tạo mới
                    existingAddress = new VendorAddress();
                    existingVendor.setVendorAddress(existingAddress);
                }

                if (addressData.containsKey("street")) {
                    existingAddress.setStreet((String) addressData.get("street"));
                }
                if (addressData.containsKey("city")) {
                    existingAddress.setCity((String) addressData.get("city"));
                }
                if (addressData.containsKey("country")) {
                    existingAddress.setCountry((String) addressData.get("country"));
                }
                if (addressData.containsKey("postCode")) {
                    existingAddress.setPostCode((String) addressData.get("postCode"));
                }

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

}
