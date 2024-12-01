package com.example.invoiceProject.Util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.beans.Encoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Base64;
import java.lang.StringBuilder;
import java.util.stream.Collectors;

@Component
public class VnpayUtil {

    public static String getHmacSHA256(String hashData, String secretKey){

        try{
            // Specify HmacSHA256
            Mac mac = Mac.getInstance("HmacSHA256");

            //Initialize with secret key
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);

            //Compute the HMAC
            byte[] dataBytes = hashData.getBytes(StandardCharsets.UTF_8);
            return Base64.getEncoder().encodeToString(dataBytes);

        }catch (NoSuchAlgorithmException | InvalidKeyException e){
            throw new RuntimeException("Failed to generate HMAC", e);
        }

    }

    public static String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }


    //
    public static String getIpAddress(HttpServletRequest request){
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    public static String getRandomNumber(int len){
        Random rand  = new Random();
        String chars = "0123456789";
        StringBuilder stringBuilder = new StringBuilder(len);
        for(int i=0; i<len; i++){
            stringBuilder.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return stringBuilder.toString();
    }


    //sắp xếp tham số tăng dần
    public static String getPaymentUrl(Map<String, String> params, boolean checkUrl){
        return params.entrySet().stream()
                .filter(entry -> entry.getKey()!=null && !entry.getValue().isEmpty())
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> (
                        checkUrl ?
                        URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII)
                        : entry.getKey() )
                        + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII)
                )
                .collect(Collectors.joining("&"));
    }

}
