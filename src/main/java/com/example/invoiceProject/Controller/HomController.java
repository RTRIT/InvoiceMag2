package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.response.AuthenticationResponse;
import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.AuthenticateService;
import com.example.invoiceProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomController {

    @Autowired
    UserService userService;


    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    AuthenticateService authenticateService;

    public String homepage(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }


    @GetMapping("dashboard")
    public String dashboard(){
        return "dashboard";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession session
//            HttpServletResponse response
            ) {


        // Tìm user trong database

        AuthenticationResponse token = authenticateService.authenticate(username, password);


        if (!token.isAuthenticated()) {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Quay lại trang login
        }

        session.setAttribute("username", username);
        session.setAttribute("auth-token", token.getToken());

        // Đăng nhập thành công
        model.addAttribute("username", username);
        return "redirect:/dashboard"; // Điều hướng tới trang chính
    }
}
