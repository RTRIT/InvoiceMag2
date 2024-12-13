 package com.example.invoiceProject.Controller;

 import com.example.invoiceProject.DTO.requests.InvoiceRequest;
 import com.example.invoiceProject.DTO.requests.InvoiceRequest;
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
 import com.itextpdf.text.*;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
 import com.nimbusds.jose.JOSEException;
 import com.nimbusds.jose.shaded.gson.Gson;
 import jakarta.servlet.http.HttpServletRequest;
 import org.modelmapper.ModelMapper;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.bind.annotation.GetMapping;
 import javax.swing.JFileChooser;
 import javax.swing.filechooser.FileNameExtensionFilter;
 import com.example.invoiceProject.Util.VnpayUtil;
 import org.springframework.web.servlet.mvc.support.RedirectAttributes;

 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.text.ParseException;
 import java.time.LocalDateTime;
 import java.time.format.DateTimeFormatter;
 import java.util.*;
 import java.util.List;
 import java.util.stream.Collectors;
 import java.util.stream.Stream;

 import java.util.stream.Collectors;

 import static java.util.stream.Collectors.toList;


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
     private InvoiceRepository invocieRepository;
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
     private InvoiceHistoryService invoiceHistoryService;
     @Autowired
     private DepartmentRepository departmentRepository;
     @Autowired
     private ModelMapper mapper;
     @Autowired
     private InvoiceToPdf invoiceToPdf;
     @Autowired InvoiceRepository invoiceRepository;

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
         invoice.setStatusExit(1);
         Invoice savedInvoice = invoiceService.createInvoice(invoice);

         invoiceHistoryService.saveInvoiceToHistory(savedInvoice, usermail, "Create");

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
     public String editInvoice(@PathVariable UUID invoiceNo, Model model, HttpServletRequest request,RedirectAttributes redirectAttributes)throws ParseException, JOSEException {
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
         if (invoice.getStatus().equals("Draft") || invoice.getStatus().equals("Sent")) {
             return "invoice/update";
         }
         else{
             redirectAttributes.addFlashAttribute("errorMessage", "Cannot update this Invoice.");
             return "redirect:/invoice/list";
         }
     }

     @PostMapping("/saveUpdate")
     public String saveUpdateInvoice(
             @RequestParam("invoiceId") UUID invoiceId,
             @RequestParam("vendormail") String vendormail,
             @RequestParam("productId") List<UUID> productIds,
             @RequestParam("departmentmail") String departmentmail,
             @RequestParam("quantities") List<Integer> quantities,
             @ModelAttribute Invoice invoice,
             HttpServletRequest request) throws ParseException, JOSEException {

         // Tìm Vendor theo email
         Optional<Vendor> vendorOptional = vendorRepository.findByEmail(vendormail);
         Vendor vendor = vendorOptional.get();
         Department department = departmentService.findByEmail(departmentmail);

         String usermail = userService.getUserByCookie(request).getEmail();
         //Tìm user theo email
         UserResponse userResponse = userService.getUserByEmail(usermail);
         User user = mapper.map(userResponse, User.class);

         Invoice invoice1 = invocieRepository.getInvoiceByInvoiceNo(invoiceId);

         // Cập nhật thông tin của hóa đơn
         invoice.setSequenceNo(invoice1.getSequenceNo());
         invoice.setVendor(vendor);
         invoice.setUser(user);
         invoice.setDepartment(department);
         invoice.setStatusExit(1);
         invoice.setInvoiceNo(invoiceId);  // Cập nhật số hóa đơn nếu cần thiết
         Invoice savedInvoice = invoiceService.updateInvoice(invoice); // Lưu hóa đơn đã cập nhật
         invoiceHistoryService.saveInvoiceToHistory(savedInvoice, usermail, "Edit");

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
    public String getAllInvoices(Model model, HttpServletRequest request)throws ParseException, JOSEException {
         List<Invoice> allInvoices = invoiceService.getAllInvoices();
         // Lọc danh sách chỉ lấy condition = 1
         List<Invoice> filteredInvoices = allInvoices.stream()
                 .filter(invoice -> invoice.getStatusExit() == 1) // Lọc condition
                 .collect(toList()); // Tạo danh sách mới sau khi lọc
         if (!filteredInvoices.isEmpty()) { // Kiểm tra danh sách không rỗng
             Collections.sort(filteredInvoices, Comparator.comparing(Invoice::getSequenceNo)); // Sắp xếp
         }
         model.addAttribute("invoices", filteredInvoices);
         //Get User form cookie
         UserResponse user = userService.getUserByCookie(request);
         model.addAttribute("user", user);
        return "invoice/home";
//        return invoiceService.getAllInvoices();
    }


     @GetMapping("/info/{invoiceNo}")
     public String getInvoiceInfo(@PathVariable UUID invoiceNo, ModelMap model) {
         List<InvoiceHistory>histories =invoiceHistoryService.getInvoiceHistoryByInvoiceId(invoiceNo);

         List<InvoiceHistory> sortedHistories = histories.stream()
                 .sorted(Comparator.comparing(InvoiceHistory::getCreatedAt).reversed()) // Đóng dấu ngoặc đúng
                 .toList();

         // Deserialize JSON dữ liệu hóa đơn
         ObjectMapper objectMapper = new ObjectMapper();

         objectMapper.registerModule(new JavaTimeModule());
         List<InvoiceRequest> invoiceHistories = sortedHistories.stream()
                 .map(history -> {
                     try {
                         return objectMapper.readValue(history.getInvoiceData(), InvoiceRequest.class);
                     } catch (JsonProcessingException e) {
                         throw new RuntimeException("Error parsing invoice JSON", e);
                     }
                 }).collect(toList());

         model.addAttribute("histories", sortedHistories);
         model.addAttribute("invoiceHistories", invoiceHistories);

         model.addAttribute("invoice", invocieRepository.getInvoiceByInvoiceNo(invoiceNo));
         List<DetailInvoice> detailInvoices = detailInvoiceService.getDetailsByInvoiceNo(invoiceNo);
         if (detailInvoices == null || detailInvoices.isEmpty()) {
             // Chuyển hướng sang trang "notFound" nếu không có DetailInvoice nào
             return "redirect:/notFound";
         }

         model.addAttribute("detailInvoices", detailInvoices);

         return "invoice/inf";
     }


     @GetMapping("/export/{invoiceNo}")
     public String exportInvoiceInfo(@PathVariable("invoiceNo") UUID uuid ,ModelMap model) throws FileNotFoundException, DocumentException {
         Invoice invoice = invoiceRepository.getInvoiceByInvoiceNo(uuid);
         invoiceToPdf.invoiceToPdf(invoice);

         return "redirect:/dashboard";
     }

     @PostMapping("/updateStatus")
     public String updateStatus(@RequestBody Map<String, String> requestData, HttpServletRequest request) throws ParseException, JOSEException {
         String invoiceIdStr = requestData.get("id");
         String newStatus = requestData.get("status");
         String usermail = userService.getUserByCookie(request).getEmail();

         // Chuyển đổi invoiceId từ String thành UUID
         UUID invoiceId;
         invoiceId = UUID.fromString(invoiceIdStr);

         // Tìm hóa đơn theo UUID và cập nhật trạng thái
         Invoice invoice = invoiceService.getInvoiceByInvoiceNo(invoiceId);
         if (newStatus.equals("Paid")){
             invoice.setPaid(invoice.getGrossTotal());
         }

         UserResponse userResponse = userService.getUserByEmail(usermail);
         User user = mapper.map(userResponse, User.class);
         invoice.setStatus(newStatus);
         Invoice savedInvoice = invoiceService.updateInvoice(invoice); // Cập nhật trạng thái của hóa đơn
         invoiceHistoryService.saveInvoiceToHistory(savedInvoice, usermail, "Status change");
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

    @PostMapping("/delete/{invoiceNo}")
    public String deleteInvoice(@PathVariable UUID invoiceNo, HttpServletRequest request, RedirectAttributes redirectAttributes) throws ParseException, JOSEException {
        System.out.println("Invoice ID: " + invoiceNo);
        String usermail = userService.getUserByCookie(request).getEmail();
        UserResponse userResponse = userService.getUserByEmail(usermail);
        User user = mapper.map(userResponse, User.class);

        Invoice invoice = invoiceService.getInvoiceByInvoiceNo(invoiceNo);


//        if (user.getFirstName().stream().anyMatch(role -> "ADMIN".equals(role.getRoleName()))) {  // Kiểm tra quyền của người dùng
        if (invoice.getStatus().equals("Draft")) {
            invoice.setStatusExit(0);
            invoiceService.updateInvoice(invoice);
            redirectAttributes.addFlashAttribute("successMessage", "Invoice deleted successfully!");
        }
        else{
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete invoice.");

        }

        return "redirect:/invoice/list";
    }
 }
