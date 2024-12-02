package com.example.invoiceProject.Service;

import com.example.invoiceProject.DTO.response.MailReponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.MailDetail;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;




@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {


    @Value("${server.domain}")
    private String serverDomain;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

    private UserService userService;

    @Value("${spring.mail.username}")
    private String sender;

    public MailReponse sendSimpleMail(MailDetail mailDetail) {

        try {
            // Creating a simple mail message object
            SimpleMailMessage emailMessage = new SimpleMailMessage();

            // Setting up necessary details of mails
            emailMessage.setFrom(sender);
            emailMessage.setTo(mailDetail.getRecipient());
            emailMessage.setCc(mailDetail.getCc().toArray(new String[0]));
            emailMessage.setSubject(mailDetail.getSubject());
            emailMessage.setText(mailDetail.getMsgBody());

            // Sending the email
            mailSender.send(emailMessage);
            return new MailReponse(true, "Email has been sent successfully..." );
        }

        catch (Exception e) {
            return new MailReponse(false, e.getMessage() );
        }
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }






//    private SimpleMailMessage createSimpleMail(String to, String subject, String msgBody) {
//
//    }
//
//
    public   void  sendMailResetPassword(String token, User user) {


        String url = serverDomain + "/user/changePassword?token=" + token;
        String email = user.getEmail();
        String  message = "Password Reset Request\n" +
                "Hi Bien,\n" +
                "Someone requested a new password for your Invoice account " + user.getEmail() + "\n" +
                "Contact support immediately, if you did not make this request.\n" +
                "Please use the verification code to complete the process.\n" +
                "Link token : " + url+ "\n" +
                "Expires in 30 minutes.\n" +
                "Visit help for login, password, and account information.\n" +
                "Contact support for help accessing your account.";
        sendEmail(email, "Reset Passwod " , message);
    }

//    public MailReponse sendMailResetPassword(String userMail) {
//        User user = userRepository.findByEmail(userMail).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
//
//        String token = UUID.randomUUID().toString();
//        userService.createPasswordResetTokenForUser(user, token);
//        mailSender.send(new constructResetTokenEmail() {})
//
//    }
//


}
