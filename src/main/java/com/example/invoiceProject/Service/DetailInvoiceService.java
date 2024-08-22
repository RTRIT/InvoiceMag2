package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.DetailInvoice;
import com.example.invoiceProject.Repository.DetailInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetailInvoiceService {

    @Autowired
    private DetailInvoiceRepository detailInvoiceRepository;

    // Create a new DetailInvoice
    public DetailInvoice createDetailInvoice(DetailInvoice detailInvoice) {
        return detailInvoiceRepository.save(detailInvoice);
    }

    // Update an existing DetailInvoice
    public Optional<DetailInvoice> updateDetailInvoice(Long id, DetailInvoice detailInvoiceDetails) {
        return detailInvoiceRepository.findById(id).map(detailInvoice -> {
            detailInvoice.setProductName(detailInvoiceDetails.getProductName());
            detailInvoice.setProductCode(detailInvoiceDetails.getProductCode());
            detailInvoice.setProductPrice(detailInvoiceDetails.getProductPrice());
            detailInvoice.setProductTax(detailInvoiceDetails.getProductTax());
            detailInvoice.setProductGrossPrice(detailInvoiceDetails.getProductGrossPrice());
            detailInvoice.setProductCurrency(detailInvoiceDetails.getProductCurrency());
            detailInvoice.setDescription(detailInvoiceDetails.getDescription());
            return detailInvoiceRepository.save(detailInvoice);
        });
    }

    // Read all DetailInvoices
    public List<DetailInvoice> getAllDetailInvoices() {
        return detailInvoiceRepository.findAll();
    }

    // Read one DetailInvoice by ID
    public Optional<DetailInvoice> getDetailInvoiceById(Long id) {
        return detailInvoiceRepository.findById(id);
    }

    // Delete DetailInvoice by ID
    public void deleteDetailInvoice(Long id) {
        detailInvoiceRepository.deleteById(id);
    }
}
