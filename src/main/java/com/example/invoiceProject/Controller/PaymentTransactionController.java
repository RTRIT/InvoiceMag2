package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Repository.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment/trans")
public class PaymentTransactionController {
    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @GetMapping("")
    public String index(ModelMap model){
        model.addAttribute("transactions", paymentTransactionRepository.findAll());
        return "payment/index";
    }
}
