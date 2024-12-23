package com.example.invoiceProject.Controller;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
    @RequestMapping("/error/accessDenied")
    public String accessDenied(HttpServletRequest request){
        System.out.println("this is context path: "+request.getContextPath());
        return "error/accessDenied";
    }
}
