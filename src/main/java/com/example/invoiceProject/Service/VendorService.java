package com.example.invoiceProject.Service;

import com.example.invoiceProject.DTO.requests.VendorCreationRequest;
import com.example.invoiceProject.DTO.response.VendorResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Model.VendorAddress;
import com.example.invoiceProject.Repository.VendorRepository;
import com.example.invoiceProject.Repository.VendorAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorAddressRepository vendorAddressRepository;

    public List<Vendor> searchVendor(String name, String email, String phonenumber) {
        if (name==null && email==null && phonenumber==null) {
            throw new AppException(ErrorCode.INVALID_SEARCH_CRITERIA);
        }

        List<Vendor> vendors = vendorRepository.searchVendor(name, phonenumber, email);

        if (vendors.isEmpty()){
            throw new AppException(ErrorCode.VENDOR_NOT_FOUND);
        }
        return vendors;
    }

    public VendorResponse getVendorByVendorID(UUID vendorid) {
        if (vendorid == null) {
            throw new AppException(ErrorCode.VENDOR_NOT_FOUND);
        }
        Optional<Vendor> vendor = vendorRepository.findByVendorid(vendorid);
        return vendor.map(VendorResponse::new).orElse(null);
    }

    // Get all vendors
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    // Create Vendor, check email and phonenumber exist
    public VendorResponse createVendor(VendorCreationRequest request) {
        if (vendorRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (vendorRepository.existsByPhonenumber(request.getPhonenumber())) {
            throw new AppException(ErrorCode.PHONENUMBER_EXISTED);
        }

        Vendor vendor = new Vendor();
        vendor.setFirstname(request.getFirstname());
        vendor.setLastname(request.getLastname());
        vendor.setTaxIdentificationNumber(request.getTaxIdentificationNumber());
        vendor.setPhonenumber(request.getPhonenumber());
        vendor.setEmail(request.getEmail());
        vendor.setBankAccount(request.getBankAccount());
        vendor.setBank(request.getBank());
        vendor.setNote(request.getNote());

        if (request.getVendorAddress() != null) {
            VendorAddress vendorAddress = new VendorAddress();
            vendorAddress.setStreet(request.getVendorAddress().getStreet());
            vendorAddress.setCity(request.getVendorAddress().getCity());
            vendorAddress.setCountry(request.getVendorAddress().getCountry());
            vendorAddress.setPostCode(request.getVendorAddress().getPostCode());
            vendorAddressRepository.save(vendorAddress);
            vendor.setVendorAddress(vendorAddress);
        }

        vendorRepository.save(vendor);
        return new VendorResponse(vendor);
    }

    

    public VendorResponse updateVendor(UUID vendorid, VendorCreationRequest request) {
        Vendor vendor = vendorRepository.findByVendorid(vendorid).orElse(null);
        if (vendor == null){
            throw new RuntimeException("Vendor cannot be null");
        }
        vendor.setFirstname(request.getFirstname());
        vendor.setLastname(request.getLastname());
        vendor.setTaxIdentificationNumber(request.getTaxIdentificationNumber());
        vendor.setPhonenumber(request.getPhonenumber());
        vendor.setEmail(request.getEmail());
        vendor.setBankAccount(request.getBankAccount());
        vendor.setBank(request.getBank());
        vendor.setNote(request.getNote());

        if (request.getVendorAddress() != null) {
            VendorAddress vendorAddress = vendor.getVendorAddress();
            if (vendorAddress == null) {
                vendorAddress = new VendorAddress();
            }
            vendorAddress.setStreet(request.getVendorAddress().getStreet());
            vendorAddress.setCity(request.getVendorAddress().getCity());
            vendorAddress.setCountry(request.getVendorAddress().getCountry());
            vendorAddress.setPostCode(request.getVendorAddress().getPostCode());
            vendorAddressRepository.save(vendorAddress);
            vendor.setVendorAddress(vendorAddress);
        }

        vendorRepository.save(vendor);
        return new VendorResponse(vendor);
    }

    public void deleteVendor(UUID vendorid) {
        vendorRepository.deleteById(vendorid);
    }
}
