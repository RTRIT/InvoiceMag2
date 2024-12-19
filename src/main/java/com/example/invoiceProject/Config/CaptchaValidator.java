package com.example.invoiceProject.Config;

import com.example.invoiceProject.DTO.response.CaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Component
public class CaptchaValidator {

    @Value("${recaptcha.url}")
    private String recaptchaUrl;

    @Value("${google.recaptcha.key.secret}")
    private  String recaptchaSecret;

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();


    public boolean isValidCaptcha(String captcha) {
        if (captcha == null || captcha.isEmpty()) {
            System.err.println("Captcha input is null or empty");
            return false;
        }

        String completeUrl = recaptchaUrl + "?secret=" + recaptchaSecret + "&response=" + captcha;

        try {
            CaptchaResponse resp = restTemplate.postForObject(completeUrl, null, CaptchaResponse.class);
            System.out.println("Captcha response is " + resp);
            if (resp != null && resp.isSuccess()) {
                return true;
            } else {
                System.err.println("CAPTCHA validation failed");
                return false;
            }
        } catch (HttpClientErrorException e) {
            System.err.println("Error during captcha verification: " + e.getMessage());
            return false;
        }
    }

}