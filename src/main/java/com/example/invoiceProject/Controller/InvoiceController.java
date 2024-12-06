 package com.example.invoiceProject.Controller;

 import com.example.invoiceProject.DTO.response.DepartmentResponse;
 import com.example.invoiceProject.DTO.response.UserResponse;
 import com.example.invoiceProject.Model.*;
 import com.example.invoiceProject.Model.DetailInvoice;
 import com.example.invoiceProject.Model.Invoice;
 import com.example.invoiceProject.Model.Product;
 import com.example.invoiceProject.Model.Vendor;
 import com.example.invoiceProject.Repository.DepartmentRepository;
 import com.example.invoiceProject.Repository.InvoiceRepository;
 import com.example.invoiceProject.Repository.ProductRepository;
 import com.example.invoiceProject.Repository.VendorRepository;
 import com.example.invoiceProject.Service.*;
 import com.example.invoiceProject.Service.PaymentService.VnPayService;
 import com.example.invoiceProject.Util.VnpayUtil;
 import com.nimbusds.jose.JOSEException;
 import jakarta.servlet.http.HttpServletRequest;
 import org.modelmapper.ModelMapper;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.bind.annotation.GetMapping;
 import com.example.invoiceProject.Util.VnpayUtil;

 import java.text.ParseException;
 import java.util.List;
 import java.util.Map;
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
     private InvoiceRepository invoiceRepository;
     @Autowired
     private VendorService vendorService;
     @Autowired
     private ProductRepository productRepository;
     @Autowired
     private DetailInvoiceService detailInvoiceService;
     @Autowired
     private DepartmentService departmentService;
     @Autowired
     private VnPayService vnPayService;
     @Autowired
     private UserService userService;
     @Autowired
     private ModelMapper mapper;
     @Autowired
     private DepartmentRepository departmentRepository;


     @GetMapping("/create")
     public String homepage1( Model model, HttpServletRequest request) throws ParseException, JOSEException {
         List<Product> products = productService.getAllProducts();
         model.addAttribute("products", products);
         List<Vendor> vendors = vendorRepository.findAll();
         model.addAttribute("vendors", vendors);

         //Get list departmemt
         List<DepartmentResponse> departments = departmentService.getList();
         model.addAttribute("departments", departments);

         //Get User form cookie
         UserResponse user = userService.getUserByCookie(request);
         model.addAttribute("user", user);

         System.out.println(departments);
         System.out.println(user);

         return "invoice/create";
     }

     @PostMapping("/save")
     public String saveInvoice(
             @RequestParam("usermail") String usermail,
             @RequestParam("vendormail") String vendormail,
             @RequestParam("departmentmail") String departmentmail,
             @RequestParam("productId") List<UUID> productIds,
             @RequestParam("quantities") List<Integer> quantities,
             @ModelAttribute Invoice invoice) {

         System.out.println(usermail);
         System.out.println(vendormail);
         System.out.println(departmentmail);



         // Tìm Vendor theo email
         Optional<Vendor> vendorOptional = vendorRepository.findByEmail(vendormail);
         Vendor vendor = vendorOptional.get();

         //Tìm department theo email
         Department department = departmentService.findByEmail(departmentmail);
//         Department department = mapper.map(departmentResponse, Department.class);

         //Tìm user theo email
         UserResponse userResponse = userService.getUserByEmail(usermail);
         User user = mapper.map(userResponse, User.class);

         // Gắn Vendor vào Invoice
         invoice.setVendor(vendor);
         invoice.setUser(user);
         invoice.setDepartment(department);

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

     @GetMapping("/edit/{invoiceNo}")
     public String editInvoice(@PathVariable UUID invoiceNo, Model model, HttpServletRequest request)throws ParseException, JOSEException {
         List<Product> products = productService.getAllProducts();
         model.addAttribute("products", products);
         List<Vendor> vendors = vendorRepository.findAll();
         model.addAttribute("vendors", vendors);

         Invoice invoice= invoiceService.getInvoiceByInvoiceNo(invoiceNo);
         model.addAttribute("invoice", invoice);
         //Get list departmemt
         List<DepartmentResponse> departments = departmentService.getList();
         model.addAttribute("departments", departments);

         UserResponse user = userService.getUserByCookie(request);
         model.addAttribute("user", user);
         System.out.println(departments);

         return "invoice/update";
     }

     @PostMapping("/saveUpdate")
     public String saveUpdateInvoice(
             @RequestParam("usermail") String usermail,
             @RequestParam("invoiceId") UUID invoiceId,
             @RequestParam("vendormail") String vendormail,
             @RequestParam("productId") List<UUID> productIds,
             @RequestParam("departmentmail") String departmentmail,
             @RequestParam("quantities") List<Integer> quantities,
             @ModelAttribute Invoice invoice) {

         // Tìm Vendor theo email
         Optional<Vendor> vendorOptional = vendorRepository.findByEmail(vendormail);
         Vendor vendor = vendorOptional.get();
         Department department = departmentService.findByEmail(departmentmail);

         //Tìm user theo email
         UserResponse userResponse = userService.getUserByEmail(usermail);
         User user = mapper.map(userResponse, User.class);

         // Cập nhật thông tin của hóa đơn
         invoice.setVendor(vendor);
         invoice.setUser(user);
         invoice.setDepartment(department);
         invoice.setInvoiceNo(invoiceId);  // Cập nhật số hóa đơn nếu cần thiết
         Invoice savedInvoice = invoiceService.createInvoice(invoice); // Lưu hóa đơn đã cập nhật

         // Xóa các chi tiết hóa đơn cũ (nếu cần)
         detailInvoiceService.deleteByInvoiceNo(invoiceId);

         // Duyệt qua từng sản phẩm và số lượng
         for (int i = 0; i < productIds.size(); i++) {
             UUID productId = productIds.get(i);
             Integer quantity = quantities.get(i);

             // Lấy thông tin sản phẩm
             Optional<Product> productOptional = productRepository.findById(productId);
             Product product = productOptional.get();

             // Tạo chi tiết hóa đơn mới
             DetailInvoice detailInvoice = new DetailInvoice();
             detailInvoice.setInvoice(savedInvoice);
             detailInvoice.setProduct(product);
             detailInvoice.setQuantity(quantity);

             // Lưu chi tiết hóa đơn
             detailInvoiceService.createDetailInvoice(detailInvoice);
         }

         // Chuyển hướng tới danh sách hóa đơn sau khi cập nhật
         return "redirect:/invoice/list";
     }


     @GetMapping("list")
    public String getAllInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        return "invoice/home";
//        return invoiceService.getAllInvoices();
    }


     @GetMapping("/info/{invoiceNo}")
     public String getInvoiceInfo(@PathVariable UUID invoiceNo, ModelMap model) {

         model.addAttribute("invoice", invoiceRepository.getInvoiceByInvoiceNo(invoiceNo));

         Department department = invoiceRepository.getInvoiceByInvoiceNo(invoiceNo).getDepartment();
         model.addAttribute("department", department);
         model.addAttribute("nameDepartment", department.getNameDepartment());

         Vendor vendor = invoiceRepository.getInvoiceByInvoiceNo(invoiceNo).getVendor();
         model.addAttribute("vendor", vendor);



         List<DetailInvoice> detailInvoices = detailInvoiceService.getDetailsByInvoiceNo(invoiceNo);
//         if (detailInvoices == null || detailInvoices.isEmpty()) {
//             // Chuyển hướng sang trang "notFound" nếu không có DetailInvoice nào
//             return "redirect:/notFound";
//         }

         model.addAttribute("detailInvoices", detailInvoices);

         return "invoice/inf";
     }

     @PostMapping("/updateStatus")
     public String updateStatus(@RequestBody Map<String, String> requestData) {
         String invoiceIdStr = requestData.get("id");
         String newStatus = requestData.get("status");

         // Chuyển đổi invoiceId từ String thành UUID
         UUID invoiceId;
         invoiceId = UUID.fromString(invoiceIdStr);

         // Tìm hóa đơn theo UUID và cập nhật trạng thái
         Invoice invoice = invoiceService.getInvoiceByInvoiceNo(invoiceId);
         invoice.setStatus(newStatus);
         invoiceService.updateInvoice(invoice); // Lưu lại hóa đơn với trạng thái mới

         return "redirect:/invoice/list";
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
