package com.example.invoiceProject.Service;

//import com.example.invoiceProject.DTO.requests.InvoiceDTO;
import com.example.invoiceProject.DTO.requests.VendorCreationRequest;
import com.example.invoiceProject.DTO.requests.VendorUpdateReq;
import com.example.invoiceProject.DTO.response.VendorResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Model.VendorAddress;
import com.example.invoiceProject.Repository.VendorRepository;
import com.example.invoiceProject.Repository.VendorAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorAddressRepository vendorAddressRepository;

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
        return vendorRepository.findAllByStatus(1);
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

    
    public VendorResponse updateVendor(UUID vendorid, VendorUpdateReq vendor2) {
        Vendor vendor = vendorRepository.findByVendorid(vendorid).orElse(null);
        if (vendor == null){
            throw new RuntimeException("Vendor cannot be null");
        }
        vendor.setFirstname(vendor2.getFirstname());
        vendor.setLastname(vendor2.getLastname());
        vendor.setTaxIdentificationNumber(vendor2.getTaxIdentificationNumber());
        vendor.setBankAccount(vendor2.getBankAccount());
        vendor.setBank(vendor2.getBank());
        vendor.setNote(vendor2.getNote());

        if (vendor2.getVendorAddress() != null) {
            VendorAddress vendorAddress = vendor.getVendorAddress();
            if (vendorAddress == null) {
                vendorAddress = new VendorAddress();
            }
            vendorAddress.setStreet(vendor2.getVendorAddress().getStreet());
            vendorAddress.setCity(vendor2.getVendorAddress().getCity());
            vendorAddress.setCountry(vendor2.getVendorAddress().getCountry());
            vendorAddress.setPostCode(vendor2.getVendorAddress().getPostCode());
            vendorAddressRepository.save(vendorAddress);
            vendor.setVendorAddress(vendorAddress);
        }

        vendorRepository.save(vendor);
        return new VendorResponse(vendor);
    }

    public void deleteVendor(UUID vendorid) {
        vendorRepository.updateVendorStatus(vendorid, 0);
    }

    public boolean existsByEmail(String email) {
        return vendorRepository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phonenumber) {
        return vendorRepository.existsByPhonenumber(phonenumber);
    }

    //get all invoices by vendoremail
    public List<Invoice> getInvoiceDetailsByVendorEmail(String vendorEmail) {
        return vendorRepository.findInvoicesByVendorEmail(vendorEmail);
    }
    
}
