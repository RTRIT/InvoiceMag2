 package com.example.invoiceProject.Controller;

 import com.example.invoiceProject.DTO.response.UserResponse;
 import com.example.invoiceProject.Model.*;
 import com.example.invoiceProject.Repository.ProductRepository;
 import com.example.invoiceProject.Repository.VendorRepository;
 import com.example.invoiceProject.Service.*;
 import com.nimbusds.jose.JOSEException;
 import jakarta.servlet.http.Cookie;
 import jakarta.servlet.http.HttpServletRequest;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.bind.annotation.GetMapping;

 import java.text.ParseException;
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
     private UserService userService;

     @GetMapping("/create")
     public String homepage1(Model model, HttpServletRequest request) throws ParseException, JOSEException {


         UserResponse seller = userService.getUserByCookie(request);
         model.addAttribute("seller", seller);
//         System.out.println(seller);

         List<Product> products = productService.getAllProducts();
         model.addAttribute("products", products);
         System.out.println(products);

         List<Vendor> vendors = vendorRepository.findAll();
         model.addAttribute("vendors", vendors);
         System.out.println(vendors);

         return "invoice/create";
     }

     @PostMapping("/save")
     public String saveInvoice(
             @RequestParam("vendormail") String vendormail,
             @RequestParam("productId") UUID productId,
             @RequestParam("quantities") Integer quantities,
             @ModelAttribute Invoice invoice) {

         Optional<Vendor> vendorOptional = vendorRepository.findByEmail(vendormail);


         Vendor vendor = vendorOptional.get();
         invoice.setVendor(vendor);
         Invoice savedInvoice = invoiceService.createInvoice(invoice);
         UUID invoiceId = savedInvoice.getInvoiceNo();
         Optional<Product> productOptional = productRepository.findById(productId);
         Product product = productOptional.get();
         DetailInvoice detailInvoice= new DetailInvoice();
         detailInvoice.setInvoice(savedInvoice);
         detailInvoice.setProduct(product);
         detailInvoice.setQuantity(quantities);
         detailInvoiceService.createDetailInvoice(detailInvoice);
         return "redirect:/invoice/list";  // Chuyển hướng tới danh sách hóa đơn
     }

    @GetMapping("/list")
    public String getAllInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        return "invoice/home";
//        return invoiceService.getAllInvoices();
    }

    @PostMapping("/updateState")
     public String updateState(){
         return "redirect:invoice/list";

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
