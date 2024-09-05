package com.example.invoiceProject.Controller.Payment;


import com.example.invoiceProject.DTO.PaymentRestDTO;
import com.example.invoiceProject.Service.PaymentService.VnPayService;
import com.example.invoiceProject.Util.VnpayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class VnPayController {
    @Autowired
    VnPayService vnPayService;


    @GetMapping("/createPayment")
    public ResponseEntity<?> createPayment(HttpServletRequest request){
        String ip = VnpayUtil.getIpAddress(request);
        String query = vnPayService.createVnPaymentUrl(ip);

        PaymentRestDTO paymentRestDTO = new PaymentRestDTO();
        paymentRestDTO.setStatus("OK");
        paymentRestDTO.setMessage("Successfully");
        paymentRestDTO.setData(query);

        return ResponseEntity.ok().body(paymentRestDTO);
    }
//    public String getClientIp(HttpServletRequest request) {
//        // Use VnpayUtil to get client IP address
//        String clientIp = VnpayUtil.getIpAddress(request);
//        return "Client IP Address: " + clientIp;
//    }


}
