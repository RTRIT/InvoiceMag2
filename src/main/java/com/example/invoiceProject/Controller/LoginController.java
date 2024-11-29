package com.example.invoiceProject.Controller;


import com.example.invoiceProject.Config.CaptchaValidator;
import com.example.invoiceProject.DTO.response.AuthenticationResponse;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.UserRepository;
import com.example.invoiceProject.Service.AuthenticateService;
import com.example.invoiceProject.Service.JwtService.JwtService;
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

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private CaptchaValidator validator;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Value("${jwt.cookieExpiry}")
    private int cookieExpiry;

    @Autowired
    AuthenticateService authenticateService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/login")
    public String login(){

        return "login";
    }


    @GetMapping("dashboard")
    public String dashboard(){
        return "dashboard";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam("g-recaptcha-response") String catpcha,
                        Model model,
                        HttpServletResponse response) throws ParseException, JOSEException {
        boolean auth = authenticateService.authenticate(username, password);

        if (auth && validator.isValidCaptcha(catpcha)) {
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
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Invalid username or password or captcha ");
        return "login";
    }

}
