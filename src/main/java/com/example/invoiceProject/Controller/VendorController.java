package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.invoiceProject.Model.VendorAddress;
import com.example.invoiceProject.Repository.VendorAddressRepository;

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

    // Phương thức để tải logo lên và lưu nó vào hệ thống tệp
    // private String saveLogoFile(MultipartFile logo) throws IOException {
    // String filename = logo.getOriginalFilename();
    // String uploadDir = "path/to/upload/directory"; // Thay đổi đường dẫn này
    // thành thư mục bạn muốn lưu file
    //
    // Path filePath = Paths.get(uploadDir, filename);
    // Files.copy(logo.getInputStream(), filePath, Files.exists(filePath) ?
    // java.nio.file.StandardCopyOption.REPLACE_EXISTING :
    // java.nio.file.StandardCopyOption.COPY_ATTRIBUTES);
    //
    // return filename; // Trả về tên file hoặc đường dẫn của file đã lưu
    // }

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

            // Tạo và lưu địa chỉ trước
            Map<String, Object> addressData = (Map<String, Object>) vendorData.get("vendorAddress");
            VendorAddress vendorAddress = new VendorAddress();
            vendorAddress.setNumberAddress((String) addressData.get("numberAddress"));
            vendorAddress.setStreet((String) addressData.get("street"));
            vendorAddress.setCity((String) addressData.get("city"));
            vendorAddress.setCountry((String) addressData.get("country"));

            // Chuyển đổi giá trị postCode
            Object postCodeObject = addressData.get("postCode");
            Long postCode = null;

            if (postCodeObject instanceof Integer) {
                // Nếu là Integer, chuyển sang Long
                postCode = ((Integer) postCodeObject).longValue();
            } else if (postCodeObject instanceof Long) {
                // Nếu đã là Long, gán giá trị trực tiếp
                postCode = (Long) postCodeObject;
            } else {
                // Xử lý khi giá trị không hợp lệ
                throw new IllegalArgumentException("Invalid postCode format");
            }

            vendorAddress.setPostCode(postCode);

            // Lưu địa chỉ vào cơ sở dữ liệu
            VendorAddress savedAddress = vendorAddressRepository.save(vendorAddress);

            // Thiết lập địa chỉ đã lưu vào vendor
            vendor.setVendorAddress(savedAddress);

            // Lưu vendor vào cơ sở dữ liệu
            vendorService.createVendor(vendor);

            return ResponseEntity.ok("Vendor created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // API cập nhật Vendor
    @PutMapping("/{vendor_id}")
    public ResponseEntity<String> updateVendor(@RequestBody Vendor vendor, @PathVariable Long vendor_id) {
        vendorService.updateVendor(vendor, vendor_id);
        return ResponseEntity.ok("Vendor updated successfully");
    }

    // API xóa Vendor
    @DeleteMapping("/{vendor_id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long vendor_id) {
        vendorService.deleteVendor(vendor_id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}
