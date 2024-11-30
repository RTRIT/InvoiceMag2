package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.requests.InvoiceDTO;
import com.example.invoiceProject.DTO.requests.VendorCreationRequest;
import com.example.invoiceProject.DTO.response.VendorResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Service.VendorService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("vendor/lists")
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    // return vendor/home.html và list vendor
    @GetMapping("vendor/list")
    public String getListVendor(ModelMap model) {
        model.addAttribute("vendors", vendorService.getAllVendors());
        return "vendor/home";
    }

    //search vendor, return vendor/home.html
    @GetMapping("/vendor/search")
    public String searchVendors(@RequestParam("keyword") String keyword, Model model) {
        // List<Vendor> vendors = vendorService.searchVendorsByKeyword(keyword);
        model.addAttribute("vendors", vendorService.searchVendorsByKeyword(keyword));
        return "vendor/search";
    }

    // return form create vendor
    @GetMapping("vendor/create")
    public String createVendorForm(ModelMap model) {
        VendorCreationRequest vendorCreationRequest = new VendorCreationRequest();
        model.addAttribute("vendor", vendorCreationRequest);
        return "vendor/create";
    }

    // create vendor
    @PostMapping("vendor/create")
    public String createVendor(@ModelAttribute("vendor") VendorCreationRequest request, ModelMap model) {
        Vendor vendor = mapper.map(request, Vendor.class);

        if (vendorService.existsByEmail(vendor.getEmail())) {
            model.addAttribute("errorMessage", "Email already exists. Please enter a different email.");
            return "vendor/create"; // Quay lại form tạo
        }
        if (vendorService.existsByPhoneNumber(vendor.getPhonenumber())) {
            model.addAttribute("errorMessage", "Phone number already exists. Please enter a different phone number.");
            return "vendor/create"; // Quay lại form tạo
        }

        try {
            
            VendorResponse vendorResponse = vendorService.createVendor(request);

            model.addAttribute("vendor", vendorResponse);
            model.addAttribute("message", "Vendor created successfully");
            return "redirect:/vendor/list";
        } catch (Exception e) {
            model.addAttribute("message", "Vendor creation failed");
            return "vendor/create";
        }
    }

    // return vendor/info
    @GetMapping("vendor/info/{vendorid}")
    public String getVendorInfo(@PathVariable UUID vendorid, ModelMap model) {
        model.addAttribute("vendor", vendorService.getVendorByVendorID(vendorid));
        return "vendor/info";
    }

    // return form edit vendor
    @GetMapping("vendor/edit/{vendorid}")
    public String editVendorForm(@PathVariable UUID vendorid, ModelMap model) {
        model.addAttribute("vendor", vendorService.getVendorByVendorID(vendorid));
        return "vendor/edit";
    }

    // Cập nhật Vendor
    @PostMapping("vendor/edit/{vendorid}")
    public String editVendor(@PathVariable UUID vendorid, @ModelAttribute("vendor") VendorCreationRequest vendor) {
        try {
            vendorService.updateVendor(vendorid, vendor);
            return "redirect:/vendor/list";
        } catch (Exception e) {
            return "vendor/edit";
        }
    }

    // Xóa Vendor
    @PostMapping("vendor/del/{vendorid}")
    public String deleteVendor(@PathVariable UUID vendorid) {
        vendorService.deleteVendor(vendorid);
        return "redirect:/vendor/list";
    }

    // @GetMapping("vendor/invoices/{email}")
    // @ResponseBody
    // public ResponseEntity<List<InvoiceDTO>> getInvoicesByVendorEmail(@PathVariable String email) {
    //     List<Invoice> invoices = vendorService.getInvoiceDetailsByVendorEmail(email);
    //     return ResponseEntity.ok(mapper.map(invoices, List.class));
    // }

    @GetMapping("vendor/invoices/{email}")
    public String getInvoicesByVendorEmail(@PathVariable String email, Model model) {
        List<Invoice> invoices = vendorService.getInvoiceDetailsByVendorEmail(email);
        model.addAttribute("invoices", invoices);
        return "vendor/invoicebymail";
    }

    @GetMapping("vendor/check")
    public ResponseEntity<?> checkEmailOrPhone(@RequestParam(required = false) String email,
                                               @RequestParam(required = false) String phoneNumber) {
        Map<String, Boolean> response = new HashMap<>();
        if (email != null) {
            response.put("emailExists", vendorService.existsByEmail(email));
        }
        if (phoneNumber != null) {
            response.put("phoneExists", vendorService.existsByPhoneNumber(phoneNumber));
        }
        return ResponseEntity.ok(response);
    }
}
