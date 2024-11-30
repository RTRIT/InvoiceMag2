//package com.example.invoiceProject.Controller;
//
//import com.example.invoiceProject.Model.DetailInvoice;
//import com.example.invoiceProject.Service.DetailInvoiceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/detailInvoices")
//public class DetailInvoiceController {
//
//    @Autowired
//    private DetailInvoiceService detailInvoiceService;
//
//    // Create a new DetailInvoice
//    @PostMapping
//    public ResponseEntity<DetailInvoice> createDetailInvoice(@RequestBody DetailInvoice detailInvoice) {
//        DetailInvoice createdDetailInvoice = detailInvoiceService.createDetailInvoice(detailInvoice);
//        return new ResponseEntity<>(createdDetailInvoice, HttpStatus.CREATED);
//    }
//
//    // Update an existing DetailInvoice
//    @PutMapping("/{id}")
//    public ResponseEntity<DetailInvoice> updateDetailInvoice(@PathVariable Long id, @RequestBody DetailInvoice detailInvoiceDetails) {
//        Optional<DetailInvoice> updatedDetailInvoice = detailInvoiceService.updateDetailInvoice(id, detailInvoiceDetails);
//        return updatedDetailInvoice.map(invoice -> new ResponseEntity<>(invoice, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    // Get all DetailInvoices
//    @GetMapping
//    public ResponseEntity<List<DetailInvoice>> getAllDetailInvoices() {
//        List<DetailInvoice> detailInvoices = detailInvoiceService.getAllDetailInvoices();
//        return new ResponseEntity<>(detailInvoices, HttpStatus.OK);
//    }
//
//    // Get DetailInvoice by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<DetailInvoice> getDetailInvoiceById(@PathVariable Long id) {
//        Optional<DetailInvoice> detailInvoice = detailInvoiceService.getDetailInvoiceById(id);
//        return detailInvoice.map(invoice -> new ResponseEntity<>(invoice, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    // Delete DetailInvoice by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteDetailInvoice(@PathVariable Long id) {
//        detailInvoiceService.deleteDetailInvoice(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
