package com.example.invoiceProject.Config;

import com.example.invoiceProject.DTO.response.CaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class CaptchaValidator {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    public boolean isValidCaptcha(String captcha) {

        String url= "https://www.google.com/recaptcha/api/siteverify";
        String params="?secret=6LcQv40qAAAAAJ2kDMrqpdmLsrxQx1erf7m5HlTM&response="+captcha;
        String completeUrl=url+params;
        CaptchaResponse resp= restTemplate.postForObject(completeUrl, null, CaptchaResponse.class);
        return resp.isSuccess();
    }
}