package com.example.invoiceProject.Controller;


import com.example.invoiceProject.DTO.response.MailReponse;
import com.example.invoiceProject.Model.MailDetail;
import com.example.invoiceProject.Service.EmailService;
import com.example.invoiceProject.Service.UserService;
import org.hibernate.validator.cfg.defs.EmailDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
// Class
public class EmailController {

    @Autowired private EmailService emailService;
    @Autowired private UserService userService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public MailReponse sendMail(@RequestBody MailDetail details)
    {
        return emailService.sendSimpleMail(details);
    }

}