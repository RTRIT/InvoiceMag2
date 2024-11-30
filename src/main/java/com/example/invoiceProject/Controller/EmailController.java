package com.example.invoiceProject.Controller;


import com.example.invoiceProject.DTO.response.MailReponse;
import com.example.invoiceProject.Model.MailDetail;
import com.example.invoiceProject.Repository.RoleRepository;
import com.example.invoiceProject.Service.EmailService;
import com.example.invoiceProject.Service.UserService;
import org.hibernate.validator.cfg.defs.EmailDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("mail")
public class EmailController {

    @Autowired private EmailService emailService;
    @Autowired private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    // Sending a simple Email
//    @PostMapping("/sendMail")
//    public MailReponse sendMail(@RequestBody MailDetail details)
//    {
//        return emailService.sendSimpleMail(details);
//    }
    @GetMapping("/mail-form")
    public String mailForm(Model model) {
        return "mail/mail-form";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/mail-form")
    public String sendEmail(@RequestParam("to") String to,
                            @RequestParam("subject") String subject,
                            @RequestParam("message") String body,
                            Model model) {
        try {
            emailService.sendEmail(to, subject, body);
            model.addAttribute("message", "Email sent successfully!");
            return  "mail/mail-form";
        } catch (Exception e) {
            model.addAttribute("message", "Failed to send email: " + e.getMessage());
        }
        return "mail/mail-form"; // Trả về view thông báo kết quả
    }



}