package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Service.VendorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")

public class VendorController {
    @Autowired
    VendorService vendorService;

    @GetMapping("/vendor/{id}")
    public ResponseEntity getVendorByVendorID(@PathVariable Long id){
        Vendor vendor = vendorService.getVendorByVendorID(id);
        return ResponseEntity.ok(vendor);
    }

    @PostMapping("/vendor")
    public ResponseEntity addVendor(@RequestBody Vendor vendor){
        String firstname = vendor.getFirstname();
        String lastname = vendor.getLastname();
        String tax_identification_number = vendor.getTax_identification_number();
        String address = vendor.getAddress();
        String street = vendor.getStreet();
        String city = vendor.getCity();
        String country = vendor.getCountry();
        String postcode = vendor.getPostcode();
        String phonenumber = vendor.getPhonenumber();
        String email = vendor.getEmail();
        String bankAcount = vendor.getBankAcount();
        String bank = vendor.getBank();
        String logo = vendor.getLogo();
        vendorService.addVendor(firstname,lastname,tax_identification_number,address,street,city,country,postcode,phonenumber,email,bankAcount,bank,logo);
        return ResponseEntity.ok("Vendor added successfully");
    }

    @PutMapping("/vendor/{id}")
    public ResponseEntity updateVendor(@RequestBody Vendor vendor, @PathVariable Long id){
        String firstname = vendor.getFirstname();
        String lastname = vendor.getLastname();
        String tax_identification_number = vendor.getTax_identification_number();
        String address = vendor.getAddress();
        String street = vendor.getStreet();
        String city = vendor.getCity();
        String country = vendor.getCountry();
        String postcode = vendor.getPostcode();
        String phonenumber = vendor.getPhonenumber();
        String email = vendor.getEmail();
        String bankAcount = vendor.getBankAcount();
        String bank = vendor.getBank();
        vendorService.updateVendor(firstname,lastname,tax_identification_number,address,street,city,country,postcode,phonenumber,email,bankAcount,bank);
        return ResponseEntity.ok("Vendor updated successfully");
    }

    @DeleteMapping("/vendor/{id}")
    public ResponseEntity deleteVendor(@PathVariable Long id){
        vendorService.deleteVendor(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}
