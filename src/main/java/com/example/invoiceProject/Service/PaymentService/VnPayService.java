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

    public String createVnPaymentUrl(String ip){
        Long amount2 = (100000L)*100;
        String bankCode = "NCB";

        //Set params needed for paymentUrl
        Map<String, String> vnpParams = paymentConfig.getVnpayConfig();

        //Set amount and bankCode
        vnpParams.put("vnp_Amount", String.valueOf(amount2));
//        vnpParams.put("vnp_BankCode", bankCode);
        vnpParams.put("vnp_IpAddr", ip);



        String queryUrl = VnpayUtil.getPaymentUrl(vnpParams, true);
        String hashData = VnpayUtil.getPaymentUrl(vnpParams, false);
        System.out.println("queryUrl: "+queryUrl);
        System.out.println("hashdata: "+hashData);
        String vnp_SecureHash = VnpayUtil.hmacSHA512(paymentConfig.getSecretKey(),hashData);

        String paymentUrl = paymentConfig.getVnp_PayUrl() +"?"+ queryUrl + "&vnp_SecureHash="+vnp_SecureHash;

        return paymentUrl;

//        System.out.println(queryUrl);
        //Gửi Reuqest lên vnpay server

        //Handle response trả về từ phía vnpay server

    }



}
