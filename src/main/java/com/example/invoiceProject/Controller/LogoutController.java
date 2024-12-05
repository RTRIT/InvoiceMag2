package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.requests.LogoutRequest;
import com.example.invoiceProject.Service.AuthenticateService;
import com.example.invoiceProject.Util.GetCookieFromRequest;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@Controller
@RequestMapping("logout")
public class LogoutController {
    @Autowired
    private AuthenticateService authenticateService;
    @Autowired
    private GetCookieFromRequest getCookieFromRequest;
    @Autowired
    private ModelMapper mapper;



    @GetMapping("/")
    public void logout(HttpServletRequest request) throws ParseException, JOSEException {
        System.out.println("get in");
//        String token = null;
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if (cookie.getName().equals("accessToken")) {
//                    token = cookie.getValue();
//                }
//            }
//        }
//        System.out.println("i get the token here??"+ token);
//        LogoutRequest logoutRequest = new LogoutRequest();
//        logoutRequest.setToken(token);
//        authenticateService.logout(mapper.map(token, LogoutRequest.class));
//        return "login";
    }
}
