 package com.example.invoiceProject.Controller;

 import com.example.invoiceProject.Model.Invoice;
 import com.example.invoiceProject.Model.Product;
 import com.example.invoiceProject.Service.InvoiceService;
 import com.example.invoiceProject.Service.ProductService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.bind.annotation.GetMapping;

 import java.util.List;
 import java.util.UUID;
 @Controller
 @RequestMapping("/invoices")
 public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
     @Autowired
     private ProductService productService;

     @GetMapping("/create")
     public String homepage1( Model model){
         List<Product> products = productService.getAllProducts();
         System.out.println(products);
         model.addAttribute("products", products);
         //Lấy user đang tạo thông tin invoice hiện tại gán vào model

         return "invoice/create";
     }

     @PostMapping("/save")
     public String saveInvoice(@ModelAttribute Invoice invoice) {
         System.out.println(invoice);
//         invoiceService.createInvoice(invoice);  // Lưu dữ liệu vào cơ sở dữ liệu
         return "redirect:/invoices";  // Chuyển hướng tới danh sách hóa đơn
     }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

//    @GetMapping("/{invoiceNo}")
//    public ResponseEntity<Invoice> getInvoiceByInvoiceNo(@PathVariable UUID invoiceNo) {
//        Invoice invoice = invoiceService.getInvoiceByInvoiceNo(invoiceNo);
//        if (invoice == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(invoice);
//    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        System.out.println("Geet into createInvoice Controller!!!");
//        invoiceService.createInvoice(invoice);
        return ResponseEntity.ok(invoice);
    }

    @PutMapping("/{invoiceNo}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable UUID invoiceNo, @RequestBody Invoice invoice) {
        invoice.setInvoiceNo(invoiceNo);
        invoiceService.updateInvoice(invoice);
        return ResponseEntity.ok(invoice);
    }

    @DeleteMapping("/{invoiceNo}")
    public ResponseEntity deleteInvoice(@PathVariable UUID invoiceNo) {
        invoiceService.deleteInvoice(invoiceNo);
        return ResponseEntity.ok().build();
    }
 }
