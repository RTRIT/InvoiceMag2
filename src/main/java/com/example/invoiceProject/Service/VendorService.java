package com.example.invoiceProject.Service;

import com.example.invoiceProject.DTO.requests.InvoiceDTO;
import com.example.invoiceProject.DTO.requests.VendorCreationRequest;
import com.example.invoiceProject.DTO.response.VendorResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Model.VendorAddress;
import com.example.invoiceProject.Repository.VendorRepository;

import jakarta.persistence.EntityNotFoundException;

import com.example.invoiceProject.Repository.InvoiceRepository;
import com.example.invoiceProject.Repository.VendorAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private InvoiceRepository invoiceRepository;

    // Get vendor by keyword
    public List<Vendor> searchVendorsByKeyword(String keyword) {
        return vendorRepository.findByKeyword(keyword);
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

    public boolean existsByEmail(String email) {
        return vendorRepository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phonenumber) {
        return vendorRepository.existsByPhonenumber(phonenumber);
    }

    //get all invoices by vendoremail
    // public List<Invoice> getInvoiceDetailsByVendorEmail(String vendorEmail) {
    //     return invoiceRepository.getInvoicesByVendorEmail(vendorEmail);
    // }

    //get all invoices by vendoremail
    public List<Invoice> getInvoiceDetailsByVendorEmail(String vendorEmail) {
        return vendorRepository.findInvoicesByVendorEmail(vendorEmail);
    }
    
}
