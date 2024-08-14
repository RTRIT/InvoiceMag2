package com.example.invoiceProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.invoiceProject.Service.CurrencyService;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/update")
    public String updateExchangeRates() {
        currencyService.updateExchangeRates();
        return "Exchange rates updated successfully!";
    }
}
