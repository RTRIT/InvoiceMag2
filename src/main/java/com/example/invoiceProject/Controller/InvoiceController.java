 package com.example.invoiceProject.Controller;

 import com.example.invoiceProject.DTO.response.DepartmentResponse;
 import com.example.invoiceProject.Model.DetailInvoice;
 import com.example.invoiceProject.Model.Invoice;
 import com.example.invoiceProject.Model.Product;
 import com.example.invoiceProject.Model.Vendor;
 import com.example.invoiceProject.Repository.ProductRepository;
 import com.example.invoiceProject.Repository.VendorRepository;
 import com.example.invoiceProject.Service.*;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.bind.annotation.GetMapping;

 import java.util.List;
 import java.util.Optional;
 import java.util.UUID;
 @Controller
 @RequestMapping("/invoice")
 public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
     @Autowired
     private ProductService productService;
     @Autowired
     private VendorRepository vendorRepository;
     @Autowired
     private VendorService vendorService;
     @Autowired
     private ProductRepository productRepository;
     @Autowired
     private DetailInvoiceService detailInvoiceService;
     @Autowired
     private DepartmentService departmentService;

     @GetMapping("/create")
     public String homepage1( Model model){
         List<Product> products = productService.getAllProducts();
         model.addAttribute("products", products);
         List<Vendor> vendors = vendorRepository.findAll();
         model.addAttribute("vendors", vendors);

         //Get list departmemt
         List<DepartmentResponse> departments = departmentService.getList();
         model.addAttribute("departments", departments);
         System.out.println(departments);

         return "invoice/create";
     }

     @PostMapping("/save")
     public String saveInvoice(
             @RequestParam("vendormail") String vendormail,
             @RequestParam("productId") List<UUID> productIds,
             @RequestParam("quantities") List<Integer> quantities,
             @ModelAttribute Invoice invoice) {

         // Tìm Vendor theo email
         Optional<Vendor> vendorOptional = vendorRepository.findByEmail(vendormail);
         Vendor vendor = vendorOptional.get();

         // Gắn Vendor vào Invoice
         invoice.setVendor(vendor);
         Invoice savedInvoice = invoiceService.createInvoice(invoice);

         // Duyệt qua từng sản phẩm và số lượng
         for (int i = 0; i < productIds.size(); i++) {
             UUID productId = productIds.get(i);
             Integer quantity = quantities.get(i);

             // Lấy thông tin sản phẩm
             Optional<Product> productOptional = productRepository.findById(productId);
             Product product = productOptional.get();

             // Tạo chi tiết hóa đơn
             DetailInvoice detailInvoice = new DetailInvoice();
             detailInvoice.setInvoice(savedInvoice);
             detailInvoice.setProduct(product);
             detailInvoice.setQuantity(quantity);

             // Lưu chi tiết hóa đơn
             detailInvoiceService.createDetailInvoice(detailInvoice);
         }

         return "redirect:/invoice/list"; // Chuyển hướng tới danh sách hóa đơn
     }

     @GetMapping("list")
    public String getAllInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        return "invoice/home";
//        return invoiceService.getAllInvoices();
    }

//    @GetMapping("/{invoiceNo}")
//    public ResponseEntity<Invoice> getInvoiceByInvoiceNo(@PathVariable UUID invoiceNo) {
//        Invoice invoice = invoiceService.getInvoiceByInvoiceNo(invoiceNo);
//        if (invoice == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(invoice);
//    }

//    @PostMapping
//    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
//        System.out.println("Geet into createInvoice Controller!!!");
//        invoiceService.createInvoice(invoice);
//        return ResponseEntity.ok(invoice);
//    }

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
