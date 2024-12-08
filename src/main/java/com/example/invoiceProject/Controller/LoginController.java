package com.example.invoiceProject.Controller;


import com.example.invoiceProject.Config.CaptchaValidator;
import com.example.invoiceProject.DTO.response.AuthenticationResponse;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.UserRepository;
import com.example.invoiceProject.Service.AuthenticateService;
import com.example.invoiceProject.Service.EmailService;
import com.example.invoiceProject.Service.JwtService.JwtService;
import com.example.invoiceProject.Service.ReCaptchaValidationService;
import com.example.invoiceProject.Service.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.UUID;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private CaptchaValidator validator;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;


    @Value("${jwt.cookieExpiry}")
    private int cookieExpiry;

    @Autowired
    AuthenticateService authenticateService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CaptchaValidator captchaValidator;


    @GetMapping()
    public String login(){
        return "login";
    }

    @GetMapping("dashboard")
    public String loginPage(){
        return "dashboard";
    }


    @PostMapping()
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam("g-recaptcha-response") String catpcha,
                        Model model,
                        HttpServletResponse response) throws ParseException, JOSEException {

        boolean auth = authenticateService.authenticate(username, password);

        boolean check = catpcha.isEmpty();
        if (auth && !check) {
            User user = userRepository.getUserByEmail(username);
            String accessToken = jwtService.generateToken(user);
            // set accessToken to cookie header
            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            model.addAttribute("user", user);
            model.addAttribute("accessToken", accessToken);
            return "redirect:/dashboard";
        }

        model.addAttribute("error", "Invalid username or password or captcha ");
        return "login";
    }


    @GetMapping("/forgot-password")
    public String forgotPassword(){
        return "login/forgotPassword";
    }

    @PostMapping("/forgot-password")
    public  String forgotPassword(@RequestParam String email,Model model){
        if (userService.userExist(email)){
            try {
            User user = userRepository.getUserByEmail(email);
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            emailService.sendMailResetPassword(token,user);
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
            }
            model.addAttribute("message", "Password reset instructions have been sent to your email., ");
        } else {
            model.addAttribute("error", "Email not found");
        }
        return "login/forgotPassword";
    }




    @GetMapping("/changePassword")
    public String showChangePasswordPage( Model model,
                                          @RequestParam("token") String token) {
        AuthenticateService.TokenStatus result = authenticateService.validatePasswordResetToken(token);

        if(result == AuthenticateService.TokenStatus.VALID) {
            model.addAttribute("message", token);
            return "redirect/user/updatePassword";
        }
        model.addAttribute("error", result.toString());
        return "redirect:/login";
    }



}
