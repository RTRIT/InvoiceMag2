package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.DetailInvoice;
import com.example.invoiceProject.Repository.DetailInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            return detailInvoiceRepository.save(detailInvoice);
        });
    }
    public List<DetailInvoice> getDetailsByInvoiceNo(UUID invoiceNo) {
        return detailInvoiceRepository.findByInvoice_invoiceNo(invoiceNo);
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

    @Transactional
    public void deleteByInvoiceNo(UUID invoiceNo) {
        detailInvoiceRepository.deleteByInvoice_invoiceNo(invoiceNo);
    }


}