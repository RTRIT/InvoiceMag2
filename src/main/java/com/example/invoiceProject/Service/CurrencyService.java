package com.example.invoiceProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.invoiceProject.Model.Currency;
import com.example.invoiceProject.Model.CurrencyResponse;
import com.example.invoiceProject.Repository.CurrencyRepository;

import java.util.Map;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/4b5350d2397ee8c285a199b0/latest/USD";

    public void updateExchangeRates() {
        RestTemplate restTemplate = new RestTemplate();
        CurrencyResponse response = restTemplate.getForObject(API_URL, CurrencyResponse.class);

        if (response != null) {
            for (Map.Entry<String, Double> entry : response.getConversion_rates().entrySet()) {
                Currency currency = new Currency();
                currency.setCurrencyCode(entry.getKey());
                currency.setCurrencyName(getCurrencyName(entry.getKey())); // Implement getCurrencyName if needed
                currency.setExchangeRate(entry.getValue());

                currencyRepository.save(currency);
            }
        }
    }

    private String getCurrencyName(String currencyCode) {
        //Optional: Implement this method to map currency code to full name if needed
        return currencyCode;
    }
}
