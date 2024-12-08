package com.example.invoiceProject.Util;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GetCookieFromRequest {

    public String getCookie(HttpServletRequest request){
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("accessToken")) {
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }
}
