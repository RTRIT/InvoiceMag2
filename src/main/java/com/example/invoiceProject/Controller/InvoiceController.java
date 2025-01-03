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
 import com.example.invoiceProject.Util.FilterCriteria;
 import com.example.invoiceProject.Util.VnpayUtil;
 import com.example.invoiceProject.enums.InvoiceKind;
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
 import org.springframework.security.access.prepost.PreAuthorize;
 import org.springframework.security.core.Authentication;
 import org.springframework.security.core.GrantedAuthority;
 import org.springframework.security.core.context.SecurityContextHolder;
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
 import java.io.IOException;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.time.LocalDate;
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
     @Autowired
     private InvoiceRepository invoiceRepository;
     @Autowired
     private RecurringInvoiceService recurringInvoiceService;

     @PreAuthorize("hasAuthority('CREATE_INVOICE')")
     @GetMapping("/create")
     public String homepage1( Model model, HttpServletRequest request) throws ParseException, JOSEException {

         //Get list products
         List<Product> products = productService.getAllProducts();
         model.addAttribute("products", products);

         //Get list vendors
         List<Vendor> vendors = vendorRepository.findAll();
         model.addAttribute("vendors", vendors);

         //Get list departmemt
         List<DepartmentResponse> departments = departmentService.getList();
         model.addAttribute("departments", departments);

         //Get User form cookie
         UserResponse user = userService.getUserByCookie(request);
         model.addAttribute("user", user);

         //Get recurrence Invoice
         RecurringInvoiceDetails recurringInvoiceDetails = new RecurringInvoiceDetails();
         model.addAttribute("recurringInvoiceDetails", recurringInvoiceDetails);

         model.addAttribute("invoiceKinds", InvoiceKind.values());

//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         if (authentication != null) {
//             for (GrantedAuthority authority : authentication.getAuthorities()) {
//                 System.out.println("Authority: " + authority.getAuthority());
//             }
//         }

//         System.out.println(departments);
//         System.out.println(user);

         return "invoice/create";
     }

     @PostMapping("/save")
     public String saveInvoice(
             @RequestParam("usermail") String usermail,
             @RequestParam("vendormail") String vendormail,
             @RequestParam("departmentmail") String departmentmail,
             @RequestParam("productId") List<UUID> productIds,
             @RequestParam("quantities") List<Integer> quantities,
             @RequestParam("isRecurring") String isRecurring,
             @RequestParam("kind") String kind,
             @ModelAttribute Invoice invoice,
             @ModelAttribute RecurringInvoiceDetails recurringInvoiceDetails) {


         System.out.println(recurringInvoiceDetails.getEndDate());
         System.out.println(recurringInvoiceDetails.getStartDate());
         System.out.println(recurringInvoiceDetails.getRecurrenceType());
         System.out.println(recurringInvoiceDetails.getRecurrenceInterval());
         System.out.println("Is it recurring invoice: "+ isRecurring);


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

         InvoiceKind invoiceKind = InvoiceKind.valueOf(kind);
         System.out.println(invoiceKind);
         invoice.setKind(invoiceKind);
         invoice.setStatusExit(1);

         if(isRecurring.equals("true")){
//             System.out.println("Get into check is recurring 1");
             invoice.setIsRecurring(true);
//             System.out.println("Get into check is recurring 2");
         }

//         System.out.println("this is invoice before saving: "+invoice);


         Invoice savedInvoice = invoiceService.createInvoice(invoice);

         System.out.println("Invoice has been saved successfully");

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
             detailInvoice.setPrice(product.getPrice());

             // Lưu chi tiết hóa đơn
             detailInvoiceService.createDetailInvoice(detailInvoice);
         }

         //Check if is recurrence invoice
         if(isRecurring.equals("true")){
             recurringInvoiceDetails.setInvoice(savedInvoice); // set invoice as foreign key
             recurringInvoiceService.createRecurringInvoiceDetails(recurringInvoiceDetails);
             System.out.println("Recurring detail has been save successfully!!");
         }

         return "redirect:/invoice/list"; // Chuyển hướng tới danh sách hóa đơn
     }

     @PreAuthorize("hasAuthority('UPDATE_INVOICE')")
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
             detailInvoice.setPrice(product.getPrice());

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

         filteredInvoices.forEach(invoice -> {
             try {
                 LocalDate paymentDate = invoice.getPaymentTime();
                 LocalDate currentDate = LocalDate.now(); // Ngày hiện tại

                 if (paymentDate.isBefore(currentDate)) {
                     invoice.setStatus("Overdue");
                     Invoice savedInvoice = invoiceService.updateInvoice(invoice);
                 }
             } catch (Exception e) {
                 e.printStackTrace(); // Xử lý lỗi định dạng ngày
             }
         });
         model.addAttribute("invoices", filteredInvoices);
         //Get User form cookie
         UserResponse user = userService.getUserByCookie(request);
         model.addAttribute("user", user);
        return "invoice/home";
//        return invoiceService.getAllInvoices();
    }



     @PreAuthorize("hasAuthority('VIEW_INVOICE')")
     @GetMapping("/info/{invoiceNo}")
     public String getInvoiceInfo(@PathVariable UUID invoiceNo, ModelMap model, HttpServletRequest request) throws ParseException, JOSEException {
         List<InvoiceHistory>histories =invoiceHistoryService.getInvoiceHistoryByInvoiceId(invoiceNo);

         List<InvoiceHistory> sortedHistories = histories.stream()
                 .sorted(Comparator.comparing(InvoiceHistory::getCreatedAt).reversed()) // Đóng dấu ngoặc đúng
                 .toList();

         UserResponse user = userService.getUserByCookie(request);

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
         model.addAttribute("user", user);
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
     public String exportInvoiceInfo(@PathVariable("invoiceNo") UUID uuid ,ModelMap model) throws IOException, DocumentException {

         Invoice invoice = invoiceRepository.getInvoiceByInvoiceNo(uuid);
         byte[] pdfBytes = invoiceToPdf.invoiceToPdf(invoice);

         //Save file in Desktop
         String desktopPath = System.getProperty("user.home") + "/Desktop/";
         String dest = desktopPath + invoice.getSequenceNo().toString() + ".pdf";
//         invoiceToPdf.invoiceToPdf(invoice);
         try{
             FileOutputStream fout = new FileOutputStream(dest);
             fout.write(pdfBytes);
             fout.close();
             System.out.println("PDF saved to Desktop: " + dest);
         } catch (Exception e){
             e.printStackTrace();
         }

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

     @PreAuthorize("hasAuthority('DELETE_INVOICE')")
    @PostMapping("/delete/{invoiceNo}")
    public String deleteInvoice(@PathVariable UUID invoiceNo, HttpServletRequest request, RedirectAttributes redirectAttributes) throws ParseException, JOSEException {
        System.out.println("Invoice ID: " + invoiceNo);
        String usermail = userService.getUserByCookie(request).getEmail();
        UserResponse userResponse = userService.getUserByEmail(usermail);
        User user = mapper.map(userResponse, User.class);

        Invoice invoice = invoiceService.getInvoiceByInvoiceNo(invoiceNo);


//        if (user.getFirstName().stream().anyMatch(role -> "ADMIN".equals(role.getRoleName()))) {  // Kiểm tra quyền của người dùng
        if (invoice.getStatus().equals("Draft") || invoice.getStatus().equals("Sent")) {
            invoice.setStatusExit(0);
            invoiceService.updateInvoice(invoice);
            redirectAttributes.addFlashAttribute("successMessage", "Invoice deleted successfully!");
        }
        else{
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete invoice.");
        }

        return "redirect:/invoice/list";
    }

    @PostMapping("/search")
    public String search(@RequestParam(value = "idInvoice", required = false, defaultValue = "") String idInvoice,
                         @RequestParam(value = "dateStart", required = false, defaultValue = "") String dateStart,
                         @RequestParam(value = "dateEnd", required = false, defaultValue = "") String dateEnd,
                         @RequestParam(value = "status", required = false, defaultValue = "") String status,
                         @RequestParam(value = "paymentType", required = false, defaultValue = "") String paymentType,
//                         @RequestParam(value = "kind", required = false, defaultValue = "") String kind,
                         HttpServletRequest request,
                         ModelMap model,
                         RedirectAttributes redirectAttributes) throws ParseException, JOSEException {
        System.out.println(idInvoice+" "+dateStart+" "+dateEnd+" "+status+" "+paymentType);

        //Format dateStart and dateEnd
        String pattern = "yyyy-MM-dd";
        LocalDate startDate = null;
        LocalDate endDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        startDate = LocalDate.parse(dateStart, formatter);
        endDate = LocalDate.parse(dateEnd, formatter);

//        InvoiceKind invoiceKind = InvoiceKind.valueOf(kind);


        //Using specification for chaining condition
        FilterCriteria filterCriteria = new FilterCriteria();
        filterCriteria.setStartDate(startDate);
        filterCriteria.setEndDate(endDate);
        filterCriteria.setStatus(status);
        filterCriteria.setPaymentType(paymentType);
//        filterCriteria.setKind(invoiceKind);

//        List<Invoice> listInvoice = invoiceService.getInvoices(filterCriteria);

        //Get list invoice by condition
        List<Invoice> listInvoice = invoiceService.getListInvoiceByCondition(idInvoice, dateStart, dateEnd, status, paymentType);
        model.addAttribute("invoices", listInvoice);


        UserResponse user = userService.getUserByCookie(request);
        model.addAttribute("user", user);
        return "invoice/home";
    }

     @GetMapping("/report")
     public String reportInvoice(ModelMap model) throws IOException, DocumentException {

         // Lấy doanh thu và số lượng hóa đơn theo ngày
         Object[] resultByDay = invoiceService.getTodayRevenue();
         Double totalRevenueDay = (Double) resultByDay[0];
         Integer invoiceCountDay = (Integer) resultByDay[1];

         // Lấy doanh thu và số lượng hóa đơn trong khoảng thời gian
         Object[] resultByRange = invoiceService.getThisWeekRevenue();
         Double totalRevenueRange = (Double) resultByRange[0];
         Integer invoiceCountRange = (Integer) resultByRange[1];

         Object[] resultByMonth = invoiceService.getThisMonthRevenue();
         Double totalRevenueMonth = (Double) resultByMonth[0];
         Integer invoiceCountMonth = (Integer) resultByMonth[1];

         Object[] resultByYear = invoiceService.getThisYearRevenue();
         Double totalRevenueYear = (Double) resultByYear[0];
         Integer invoiceCountYear = (Integer) resultByYear[1];

         model.addAttribute("totalRevenueDay", totalRevenueDay);
         model.addAttribute("invoiceCountDay", invoiceCountDay);
         model.addAttribute("totalRevenueRange", totalRevenueRange);
         model.addAttribute("invoiceCountRange", invoiceCountRange);
         model.addAttribute("totalRevenueMonth", totalRevenueMonth);
         model.addAttribute("invoiceCountMonth", invoiceCountMonth);
         model.addAttribute("totalRevenueYear", totalRevenueYear);
         model.addAttribute("invoiceCountYear", invoiceCountYear);

         return "invoice/report";
     }
 }
