package com.example.invoiceProject.Controller;


import com.example.invoiceProject.DTO.response.DepartmentResponse;
import com.example.invoiceProject.DTO.response.MailReponse;
import com.example.invoiceProject.Model.*;
import com.example.invoiceProject.Repository.RoleRepository;
import com.example.invoiceProject.Service.EmailService;
import com.example.invoiceProject.Service.PaymentService.VnPayService;
import com.example.invoiceProject.Service.UserService;
import jakarta.mail.Multipart;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.validator.cfg.defs.EmailDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.example.invoiceProject.Util.VnpayUtil;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("mail")
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private VnPayService vnPayService;

    // Sending a simple Email
//    @PostMapping("/sendMail")
//    public MailReponse sendMail(@RequestBody MailDetail details)
//    {
//        return emailService.sendSimpleMail(details);
//    }


    @PostMapping("/process")
    public String processDataToForm(HttpServletRequest request,
                                          ModelMap model,
                                          @RequestParam("grossTotal") String grossTotal,
                                          @RequestParam("sequence") String sequenceNo,
                                          @RequestParam("userEmail") String userEmail,
                                          @RequestParam("departmentEmail") String departmentEmail,
                                          @RequestParam("vendorEmail") String vendorEmail){
//        System.out.println("This is grossTotal: "+grossTotal);
//        System.out.println("This is sequenceNo: "+sequenceNo);
//        System.out.println("This is user email: "+userEmail);
//        System.out.println("This is department email: "+departmentEmail);
//        System.out.println("This is vendor email: "+vendorEmail);
        String ip = VnpayUtil.getIpAddress(request);
        String paymentUrl = vnPayService.createVnPaymentUrl(ip, grossTotal, sequenceNo);
//        System.out.println(paymentUrl);
        model.addAttribute("department", departmentEmail);
        model.addAttribute("vendor",vendorEmail);
        model.addAttribute("payUrl", paymentUrl);
        model.addAttribute("sequenceNo", sequenceNo);
        return "mail/mail-form";

    }

    @GetMapping("/mail-form")
    public String mailForm(Model model) {
        return "mail/mail-form";
    }

    @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
    @PostMapping("/mail-form")
    public String sendEmail(@RequestParam("to") String to,
                            @RequestParam("subject") String subject,
                            @RequestParam("message") String body,
                            @RequestParam("attachment") MultipartFile attachment,
                            Model model) {
        if (attachment != null && !attachment.isEmpty()) {
            System.out.println("Received file: " + attachment.getOriginalFilename());
        } else {
            System.out.println("No file received.");
        }
        try {
//            emailService.sendEmail(to, subject, body);
            emailService.sendEmailWithPdf(to, subject, body, new Product(), new Invoice(), new Vendor(), new DetailInvoice(), new Department());
            model.addAttribute("message", "Email sent successfully!");
            return  "mail/mail-form";
        } catch (Exception e) {
            model.addAttribute("message", "Failed to send email: " + e.getMessage());
        }
        return "mail/mail-form"; // Trả về view thông báo kết quả
    }



}