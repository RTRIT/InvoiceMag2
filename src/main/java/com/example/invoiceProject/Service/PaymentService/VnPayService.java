package com.example.invoiceProject.Service.PaymentService;
import com.example.invoiceProject.Config.PaymentConfig;
import com.example.invoiceProject.Util.VnpayUtil;



import com.example.invoiceProject.Util.VnpayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
public class VnPayService {

    @Autowired
    PaymentConfig paymentConfig;
//    @Autowired
//    VnpayUtil vnpayUtil;
//    @Autowired
//    PaymentConfig paymentConfig;



    public String createVnPaymentUrl(String ip){
        Long amount2 = (100000L)*100;
        String bankCode = "NCB";


        //Set amount and bankCode
        Map<String, String> vnpParams = paymentConfig.getVnpayConfig();
        vnpParams.put("vnp_Amount", String.valueOf(amount2));
        vnpParams.put("vnp_BankCode", bankCode);
        vnpParams.put("vnp_IpAddr", ip);


        //build query
        List fieldNames = new ArrayList(vnpParams.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnpParams.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayUtil.hmacSHA512(paymentConfig.getSecretKey(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = paymentConfig.getVnp_PayUrl() + "?" + queryUrl;
//        String queryUrl = VnpayUtil.getPaymentUrl(vnParams, true);
//        String hashData = VnpayUtil.getPaymentUrl(vnPayParams, false);
//        System.out.println(queryUrl);
//        System.out.println(hashData);
//        String vnp_SecureHash = VnpayUtil.hmacSHA512(paymentConfig.getSecretKey(),hashData);
//        String paymentUrl = paymentConfig.getVnp_PayUrl() + queryUrl ;
//        String paymentUrl = paymentConfig.getVnp_PayUrl() + queryUrl + "&vnp_SecureHash="+vnp_SecureHash;

        return paymentUrl;

//        System.out.println(queryUrl);
        //Gửi Reuqest lên vnpay server

        //Handle response trả về từ phía vnpay server

    }


}
