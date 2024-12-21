package com.example.invoiceProject.Service;

import com.example.invoiceProject.DTO.response.EmailVerificationResponse;
import com.example.invoiceProject.Model.*;
import com.example.invoiceProject.Repository.UserRepository;
import com.lowagie.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class EmailService {

    @Value("${server.domain}")
    private String serverDomain;
    private final JavaMailSender mailSender;
    private final BlockingQueue<SimpleMailMessage> queue;
    private final RestTemplate restTemplate;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${email.verification.api.url}")
    private String apiUrl;

    @Value("${email.verification.api.key}")
    private String apiKey;

    @Autowired
    private UserRepository userRepository;
    private UserService userService;
    private InvoiceService invoiceService;
    @Autowired
    private ProjectInfoAutoConfiguration projectInfoAutoConfiguration;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.queue = new LinkedBlockingQueue<>();
        this.restTemplate = new RestTemplate();
        this.start();
    }

    private void start() {
        Thread newThread = new Thread(() -> {
            while (true) {
                try {
                    SimpleMailMessage mail = queue.take();
                    mailSender.send(mail);
                    log.info("Sent mail: " + mail);
                } catch (InterruptedException e) {
                    break;
                } catch (Throwable e) {
                    log.error("Send mail error: ", e);
                }
            }
        });
        newThread.setName("mail-sender");
        newThread.start();
    }

    @Async
    public void sendEmail(String to, String subject, String text) throws Exception {
        if (verifyEmail(to)) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            queue.add(message);
            log.info("Email added to queue for sending.");
        } else {
            throw new Exception("Invalid email address: " + to);
        }
    }

    private boolean verifyEmail(String email) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("access_key", apiKey)
                .queryParam("email", email)
                .toUriString();

        EmailVerificationResponse response = restTemplate.getForObject(url, EmailVerificationResponse.class);

        return response != null && response.isFormatValid() && response.isSmtpCheck();
    }

    private byte[] createPdfFromHtml(String htmlContent) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.createPDF(outputStream);
        return outputStream.toByteArray();
    }

    public void sendEmailWithPdf(String to, String subject, String text, Product product, Invoice invoice, Vendor vendor) throws MessagingException, IOException, DocumentException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        String htmlContent = invoiceService.generateInvoiceHtml(product, invoice, vendor);
        byte[] pdfBytes = createPdfFromHtml(htmlContent);
        String nameInvoice = "invoice" + invoice.getInvoiceNo() + ".pdf";

        helper.addAttachment(nameInvoice, new ByteArrayDataSource(pdfBytes, "application/pdf"));
        mailSender.send(message);
    }

    public void sendMailResetPassword(String token, User user) throws Exception {
        String url = serverDomain + "/user/changePassword?token=" + token;
        String email = user.getEmail();
        String message = "Password Reset Request\n" +
                "Hi Bien,\n" +
                "Someone requested a new password for your Invoice account " + user.getEmail() + "\n" +
                "Contact support immediately, if you did not make this request.\n" +
                "Please use the verification code to complete the process.\n" +
                "Link token : " + url + "\n" +
                "Expires in 30 minutes.\n" +
                "Visit help for login, password, and account information.\n" +
                "Contact support for help accessing your account.";
        sendEmail(email, "Reset Password", message);
    }
}
