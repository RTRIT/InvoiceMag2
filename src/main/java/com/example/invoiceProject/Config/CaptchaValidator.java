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
        String completeUrl = recaptchaUrl + "?secret=" + recaptchaSecret + "&response=" + captcha;
        int retryCount = 3;
        while (retryCount > 0) {
            try {
                CaptchaResponse resp = restTemplate.postForObject(completeUrl, null, CaptchaResponse.class);
                if (resp != null && resp.isSuccess()) {
                    return true;
                } else {
                    System.err.println("CAPTCHA validation failed: " + (resp != null ? resp.getErrorCodes() : "Unknown error"));
                    retryCount--;
                }
            } catch (HttpClientErrorException e) {
                System.err.println("HTTP client error during captcha verification: " + e.getMessage());
                retryCount--;
            } catch (Exception e) {
                System.err.println("Error during captcha verification: " + e.getMessage());
                retryCount--;
            }
        }
        return false;
    }


}