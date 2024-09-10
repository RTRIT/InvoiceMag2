package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.Type;
import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Repository.TypeRepository;
import com.example.invoiceProject.Repository.VendorRepository;
import com.example.invoiceProject.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    // Phương thức để tải logo lên và lưu nó vào hệ thống tệp
//    private String saveLogoFile(MultipartFile logo) throws IOException {
//        String filename = logo.getOriginalFilename();
//        String uploadDir = "path/to/upload/directory"; // Thay đổi đường dẫn này thành thư mục bạn muốn lưu file
//
//        Path filePath = Paths.get(uploadDir, filename);
//        Files.copy(logo.getInputStream(), filePath, Files.exists(filePath) ? java.nio.file.StandardCopyOption.REPLACE_EXISTING : java.nio.file.StandardCopyOption.COPY_ATTRIBUTES);
//
//        return filename; // Trả về tên file hoặc đường dẫn của file đã lưu
//    }

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

    // API tạo Vendor với khả năng upload file logo
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> createVendor(
//            @RequestPart("vendor") Map<String, Object> vendorData,
//            @RequestPart(value = "logo", required = false) MultipartFile logo) {
//        try {
//            // Kiểm tra dữ liệu đầu vào
//            if (vendorData.get("firstname") == null || vendorData.get("lastname") == null) {
//                return ResponseEntity.badRequest().body("First name and Last name are required.");
//            }
//
//            // Tìm kiếm đối tượng Type dựa trên typeID từ dữ liệu đầu vào
//            Long typeId = Long.parseLong(vendorData.get("typeID").toString());
//            Type type = typeRepository.findById(typeId)
//                    .orElseThrow(() -> new RuntimeException("Type not found with ID: " + typeId));
//
//            // Xử lý upload logo nếu có
//            String logoFilename = null;
//            if (logo != null && !logo.isEmpty()) {
//                logoFilename = saveLogoFile(logo);
//            }
//
//            // Tạo đối tượng Vendor và ánh xạ các giá trị
//            Vendor vendor = new Vendor(
//                    vendorData.get("firstname").toString(),
//                    vendorData.get("lastname").toString(),
//                    vendorData.get("taxIdentificationNumber").toString(),
//                    vendorData.get("address").toString(),
//                    vendorData.get("street").toString(),
//                    vendorData.get("city").toString(),
//                    vendorData.get("country").toString(),
//                    vendorData.get("postcode").toString(),
//                    vendorData.get("phonenumber").toString(),
//                    vendorData.get("email").toString(),
//                    vendorData.get("bankAccount").toString(),
//                    vendorData.get("bank").toString(),
//                    logoFilename, // Đường dẫn của logo sau khi lưu, nếu không có sẽ là null
//                    type
//            );
//
//            // Lưu Vendor vào cơ sở dữ liệu
//            vendorRepository.save(vendor);
//
//            return ResponseEntity.ok(vendor);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

    @PostMapping
    public ResponseEntity<?> createVendor(@RequestBody Map<String, Object> vendorData) {
        try {
            // Tìm kiếm đối tượng Type dựa trên typeID từ dữ liệu đầu vào
            Long typeId = Long.parseLong(vendorData.get("typeID").toString());
            Type type = typeRepository.findById(typeId)
                    .orElseThrow(() -> new RuntimeException("Type not found with ID: " + typeId));

            // Tạo đối tượng Vendor và ánh xạ các giá trị
            Vendor vendor = new Vendor( vendorData.get("firstname").toString(),
                    vendorData.get("lastname").toString(),
                    vendorData.get("taxIdentificationNumber").toString(),
                    vendorData.get("address").toString(),
                    vendorData.get("street").toString(),
                    vendorData.get("city").toString(),
                    vendorData.get("country").toString(),
                    vendorData.get("postcode").toString(),
                    vendorData.get("phonenumber").toString(),
                    vendorData.get("email").toString(),
                    vendorData.get("bankAccount").toString(),
                    vendorData.get("bank").toString(),
                    vendorData.get("logo").toString(),
                    type);

            // Lưu Vendor vào cơ sở dữ liệu
            vendorRepository.save(vendor);

            return ResponseEntity.ok(vendor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
