package com.example.invoiceProject.Service.PaymentService;

import com.example.invoiceProject.Config.PaymentConfig;
import com.example.invoiceProject.DTO.response.IpnResponse;
import com.example.invoiceProject.Service.PaymentTransactionService;
import com.example.invoiceProject.Util.VnpayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.invoiceProject.Util.VnpayUtil;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VnPayIpnHandler {

    @Autowired
    private static VnpayUtil vnpayUtil;
    @Autowired
    private PaymentConfig paymentConfig;
    @Autowired
    private PaymentTransactionService paymentTransactionService;

    public IpnResponse process(Map<String, String> params){

        //Đảm bảo dữ liệu không bị thay đổi khi trả result vnpay server tới server
        String vnp_SecureHash = params.get("vnp_SecureHash");
        params.remove("vnp_SecureHash");
        String hashData = VnpayUtil.getPaymentUrl(params, true);
        String secureHash = VnpayUtil.hmacSHA512(paymentConfig.getSecretKey(), hashData);
        if(vnp_SecureHash.equals(secureHash)){
            System.out.println("The value return is the same");
            paymentTransactionService.save(params);
        }else{
            System.out.println("The value return is not the same");
        }

        return IpnResponse.builder()
                .responseCode("100")
                .message("success")
                .build();
    }
}
