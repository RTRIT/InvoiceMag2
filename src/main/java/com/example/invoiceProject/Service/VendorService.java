package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.Vendor;
import com.example.invoiceProject.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor getVendorByVendorID(Long id){
        return vendorRepository.getVendorByVendorID(id);
    }

//    public Vendor getFirstnameAndLastnameByFirstnameAndLastname(String firstname, String lastname) {
//        return vendorRepository.getFirstnameAndLastnameByFirstnameAndLastname(firstname, lastname);
//    }

//    public Vendor getFirstnameByFirstname(String firstname) {
//        return vendorRepository.getFirstnameByFirstname(firstname);
//    }
//
//    public Vendor getLastnameByLastname(String lastname) {
//        return vendorRepository.getLastnameByLastname(lastname);
//    }
//
//    public Vendor getTIDnameByTID(String taxIdentificationNumber) {
//        return vendorRepository.getTIDnameByTID(taxIdentificationNumber);
//    }

//    public void addVendor(String firstname, String lastname, String tax_identification_number, String address,
//                          String street, String city, String country, String postcode, String phonenumber, String email,
//                          String bankAcount, String bank, String logo) {
//        vendorRepository.addVendor(firstname,lastname,tax_identification_number,address,street,city,country,postcode,
//                phonenumber,email,bank,bankAcount,logo);
//    }
//
    public void updateVendor(String firstname, String lastname, String tax_identification_number, String address,
                             String street, String city, String country, String postcode, String phonenumber, String email,
                             String bankAcount, String bank) {
        vendorRepository.updateVendor(firstname,lastname,tax_identification_number,address,street,city,country,postcode,
                phonenumber,email,bank,bankAcount);
    }

    public void deleteVendor(Long id) {
        vendorRepository.deleteVendor(id);
    }

}
