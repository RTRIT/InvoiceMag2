package com.example.invoiceProject.Service;

import com.example.invoiceProject.DTO.response.MailReponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.*;
import com.example.invoiceProject.Repository.UserRepository;
import com.lowagie.text.DocumentException;
import jakarta.mail.MessagingException;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


@Service
@Slf4j
public class EmailService {


    @Value("${server.domain}")
    private String serverDomain;
    private final JavaMailSender mailSender;
    private final BlockingQueue<SimpleMailMessage> queue;

    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private JavaMailSender mailSender;

    private UserService userService;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.queue = new LinkedBlockingQueue<>();
        this.start();
    }

    private void start() {
        Thread newThread = new Thread(() -> {
            while (true) {
                try {
                    SimpleMailMessage mail = queue.take();
                    mailSender.send(mail);
                    System.out.println("sent mail: " + mail);
                } catch (InterruptedException e) {
                    break;
                } catch (Throwable e) {
                    System.out.println("send mail error: " + e);
                }
            }
        });
        newThread.setName("mail-sender");
        newThread.start();
    }

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


    @Async
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        queue.add(message);
//        mailSender.send(message);
    }


    private byte[] createPdfFromHtml(String htmlContent) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();

        // Tải HTML vào Flying Saucer để chuyển đổi thành PDF
        renderer.setDocumentFromString(htmlContent);

        // Tạo PDF dưới dạng byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.createPDF(outputStream);

        // Trả lại PDF dưới dạng byte array
        return outputStream.toByteArray();
    }


    public void sendEmailWithPdf(String to, String subject, String text, Product product, Invoice invoice, Vendor vendor) throws MessagingException, IOException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        // Tạo nội dung HTML từ các đối tượng
        String htmlContent = invoiceService.generateInvoiceHtml(customer, invoice, bankDetails);

        // Tạo PDF từ HTML
        byte[] pdfBytes = createPdfFromHtml(htmlContent);

        // Đính kèm file PDF vào email
        message.addAttachment("invoice.pdf", new ByteArrayDataSource(pdfBytes, "application/pdf"));

        // Gửi email
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
