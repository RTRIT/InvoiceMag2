package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.response.AuthenticationResponse;
import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.AuthenticateService;
import com.example.invoiceProject.Service.UserService;
import jakarta.servlet.http.Cookie;
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
            HttpServletResponse response) {

        // Tìm người dùng trong database và xác thực
        AuthenticationResponse token = authenticateService.authenticate(username, password);

        // Kiểm tra nếu không xác thực thành công
        if (!token.isAuthenticated()) {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Quay lại trang login nếu không thành công
        }

        model.addAttribute("token", token.getToken());
        // Lưu token vào cookie nếu xác thực thành công
        Cookie cookie = new Cookie("auth-token", token.getToken());
        cookie.setHttpOnly(true); // Đảm bảo cookie không thể truy cập từ JavaScript
        cookie.setMaxAge(60 * 60 * 24 * 7); // Hết hạn trong 1 tuần
        cookie.setPath("/"); // Áp dụng cho toàn bộ ứng dụng
        response.addCookie(cookie);

        // Đăng nhập thành công, chuyển hướng tới trang dashboard
        return "redirect:/dashboard";
    }
}
