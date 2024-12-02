package com.example.invoiceProject.Service.PaymentService;

import com.example.invoiceProject.Config.PaymentConfig;
import com.example.invoiceProject.DTO.response.IpnResponse;
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

    public IpnResponse process(Map<String, String> params){

        //Đảm bảo dữ liệu không bị thay đổi khi gọi từ vnpay tới server
        String vnp_SecureHash = params.get("vnp_SecureHash");
        params.remove("vnp_SecureHash");
        String hashData = VnpayUtil.getPaymentUrl(params, true);
        String secureHash = VnpayUtil.hmacSHA512(paymentConfig.getSecretKey(), hashData);
        if(vnp_SecureHash.equals(secureHash)){
            //gọi hàm uodate invoice status

        }

        return IpnResponse.builder()
                .responseCode("100")
                .message("sucess")
                .build();
    }
}
