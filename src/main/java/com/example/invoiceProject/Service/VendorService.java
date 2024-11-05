package com.example.invoiceProject.Service;

import com.example.invoiceProject.DTO.requests.VendorCreationRequest;
import com.example.invoiceProject.DTO.response.VendorAddressResponse;
import com.example.invoiceProject.DTO.response.VendorResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Model.VendorAddress;
import com.example.invoiceProject.Repository.VendorRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public List<Vendor> searchVendor(String name, String phonenumber, String email) {

        if (name == null && phonenumber == null && email == null) {
            throw new AppException(ErrorCode.INVALID_SEARCH_CRITERIA);
        }

        List<Vendor> vendors = vendorRepository.searchVendor(name, phonenumber, email);

        if (vendors.isEmpty()) {
            throw new AppException(ErrorCode.VENDOR_NOT_FOUND);
        }

        return vendors;
    }

    public Vendor getVendorByVendorID(UUID vendorid) {
        if (vendorid == null) {
            throw new AppException(ErrorCode.VENDOR_NOT_FOUND);
        }
        return vendorRepository.findByVendorid(vendorid)
                .orElseThrow(() -> new AppException(ErrorCode.VENDOR_NOT_FOUND));
    }

    @Transactional
    public VendorResponse createVendor(VendorCreationRequest request) {
        // Check if Vendor with email or phone number already exists
        if (vendorRepository.existsByEmail(request.getEmail())
                || vendorRepository.existsByPhonenumber(request.getPhonenumber())) {
            throw new AppException(ErrorCode.VENDOR_EXISTED);
        }

        // Map Vendor from request
        Vendor vendor = mapToVendor(request);

        // Save Vendor to the database
        vendor = vendorRepository.save(vendor);

        // Return the VendorResponse
        return mapToVendorResponse(vendor);
    }

    // Private helper method to map VendorCreationRequest to Vendor
    private Vendor mapToVendor(VendorCreationRequest request) {
        Vendor vendor = new Vendor();
        vendor.setFirstname(request.getFirstname());
        vendor.setLastname(request.getLastname());
        vendor.setTaxIdentificationNumber(request.getTaxIdentificationNumber());
        vendor.setPhonenumber(request.getPhonenumber());
        vendor.setEmail(request.getEmail());
        vendor.setBankAccount(request.getBankAccount());
        vendor.setBank(request.getBank());
        vendor.setNote(request.getNote());

        // Map address if present in the request
        if (request.getAddress() != null) {
            VendorAddress vendorAddress = new VendorAddress();
            vendorAddress.setStreet(request.getAddress().getStreet());
            vendorAddress.setCity(request.getAddress().getCity());
            vendorAddress.setCountry(request.getAddress().getCountry());
            vendorAddress.setPostCode(request.getAddress().getPostCode());
            vendor.setVendorAddress(vendorAddress);
        }
        return vendor;
    }

    // Phương thức mapToVendorResponse để chuyển đổi Vendor thành VendorResponse
    private VendorResponse mapToVendorResponse(Vendor vendor) {
        VendorResponse response = new VendorResponse();
        response.setVendorid(vendor.getVendorid());
        response.setFirstname(vendor.getFirstname());
        response.setLastname(vendor.getLastname());
        response.setTaxIdentificationNumber(vendor.getTaxIdentificationNumber());
        response.setPhonenumber(vendor.getPhonenumber());
        response.setEmail(vendor.getEmail());
        response.setBankAccount(vendor.getBankAccount());
        response.setBank(vendor.getBank());
        response.setNote(vendor.getNote());

        // Nếu Vendor có địa chỉ, ánh xạ sang VendorAddressResponse
        if (vendor.getVendorAddress() != null) {
            VendorAddressResponse addressResponse = new VendorAddressResponse();
            addressResponse.setStreet(vendor.getVendorAddress().getStreet());
            addressResponse.setCity(vendor.getVendorAddress().getCity());
            addressResponse.setCountry(vendor.getVendorAddress().getCountry());
            addressResponse.setPostCode(vendor.getVendorAddress().getPostCode());
            response.setVendorAddress(addressResponse);
        }

        return response;
    }

    @Transactional
    public void updateVendor(Vendor vendor, UUID vendorid) {
        if (vendor == null) {
            throw new RuntimeException("Vendor cannot be null");
        }

        Vendor existingVendor = vendorRepository.findByVendorid(vendorid)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        // Copy properties from the input vendor to the existing vendor
        existingVendor.setBank(vendor.getBank());
        existingVendor.setBankAccount(vendor.getBankAccount());
        existingVendor.setEmail(vendor.getEmail());
        existingVendor.setFirstname(vendor.getFirstname());
        existingVendor.setLastname(vendor.getLastname());
        // existingVendor.setLogo(vendor.getLogo());
        existingVendor.setPhonenumber(vendor.getPhonenumber());
        existingVendor.setTaxIdentificationNumber(vendor.getTaxIdentificationNumber());
        existingVendor.setVendorAddress(vendor.getVendorAddress());

        vendorRepository.save(existingVendor);
    }

    @Transactional
    public void deleteVendor(UUID vendorid) {
        vendorRepository.deleteVendor(vendorid);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

}