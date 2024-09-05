package com.example.invoiceProject;



import com.example.invoiceProject.Config.PaymentConfig;

import com.example.invoiceProject.Service.PaymentService.VnPayService;
//import com.example.invoiceProject.Util.VnpayUtil;
import com.example.invoiceProject.Util.VnpayUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class VnpayUnitTest {
    @Autowired
    private PaymentConfig paymentConfig;
    @Autowired
    VnPayService vnPayService;

    @Autowired
    VnpayUtil vnpayUtil;

    @Test
    public void testPaymentConfig(){
        // Act
        Map<String, String> vnpayConfig = paymentConfig.getVnpayConfig();
        Long amount = 1000000L;
        String bankCode = "VnBank";


//        System.out.println(vnpayConfig);
//        String newUrl = VnpayUtil.getPaymentUrl(vnpayConfig, true);
//        String hashdata = VnpayUtil.getPaymentUrl(vnpayConfig, false);
//        System.out.println(newUrl);
//        System.out.println(hashdata);
//        System.out.println(VnpayUtil.getIpAddress())
        vnPayService.createVnPaymentUrl("12");
        // Assert
        assertNotNull(vnpayConfig);
//        assertEquals("VND", vnpayConfig.get("vnp_CurrCode"));
//        assertEquals("vn", vnpayConfig.get("vnp_Locale"));
//        assertNotNull(vnpayConfig.get("vnp_TxnRef"));
//        assertNotNull(vnpayConfig.get("vnp_OrderInfo"));
//        assertNotNull(vnpayConfig.get("vnp_CreateDate"));
//        assertNotNull(vnpayConfig.get("vnp_ExpireDate"));
    }
}
