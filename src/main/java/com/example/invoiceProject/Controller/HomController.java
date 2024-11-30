package com.example.invoiceProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomController {
    @GetMapping("/")
    public String homepage(){
        return "login";
    }
}
