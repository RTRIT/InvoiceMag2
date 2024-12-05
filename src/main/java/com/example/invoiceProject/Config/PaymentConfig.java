package com.example.invoiceProject.Config;
import com.example.invoiceProject.Util.VnpayUtil;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;




@Configuration
public class PaymentConfig {

    @Value("${payment.vnPay.url}")
    @Getter
    private String vnp_PayUrl; //url thanh toan (môi trường test)

    @Value("${payment.vnPay.returnUrl}")
    private String vnp_ReturnUrl;

    @Value("${payment.vnPay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${payment.vnPay.secretKey}")
    @Getter
    private String secretKey;

    @Value("${payment.vnPay.version}")
    private String vnp_Version;

    @Value("${payment.vnPay.command}")
    private String vnp_Command;

    @Value("${payment.vnPay.orderType}")
    private String orderType;

    public Map<String, String> getVnpayConfig(){

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", this.vnp_Version);
        vnp_Params.put("vnp_Command", this.vnp_Command);
        vnp_Params.put("vnp_TmnCode", this.vnp_TmnCode);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", this.orderType);
        vnp_Params.put("vnp_TxnRef", VnpayUtil.getRandomNumber(8)); // anh xa ma giao dich
//        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang" + 12345);
        vnp_Params.put("vnp_ReturnUrl", this.vnp_ReturnUrl);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        //created date
        LocalDateTime now = LocalDateTime.now();
        String fomatteDate = now.format(formatter);
        vnp_Params.put("vnp_CreateDate", fomatteDate);

        //expired date
        LocalDateTime expired =  now.plusMinutes(15);
        String expiredDate = expired.format(formatter);
        vnp_Params.put("vnp_ExpireDate", expiredDate);


        return vnp_Params;
    }

}
