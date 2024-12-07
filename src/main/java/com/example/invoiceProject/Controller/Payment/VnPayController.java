package com.example.invoiceProject.Controller.Payment;


import com.example.invoiceProject.DTO.PaymentRestDTO;
import com.example.invoiceProject.Model.Invoice;
import com.example.invoiceProject.Service.PaymentService.VnPayIpnHandler;
import com.example.invoiceProject.Service.PaymentService.VnPayService;
import com.example.invoiceProject.Util.VnpayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RequestMapping("/payment")
public class VnPayController {
    @Autowired
    VnPayService vnPayService;
    @Autowired
    VnPayIpnHandler vnPayIpnHandler;



    @GetMapping("/createPayment")
    public String createPayment(HttpServletRequest request, ModelMap model, Invoice invoice){
        String ip = VnpayUtil.getIpAddress(request);
        String query = vnPayService.createVnPaymentUrl(ip,"100000", "invoiceId");

        PaymentRestDTO paymentRestDTO = new PaymentRestDTO();
        paymentRestDTO.setStatus("OK");
        paymentRestDTO.setMessage("Successfully");
        paymentRestDTO.setData(query);
//        System.out.println(paymentRestDTO);
        model.addAttribute("payment_url", paymentRestDTO);

        return query;
    }

    @GetMapping("/returnPaymentUrl")
    public String returnUrl(ModelMap model,HttpServletRequest request, @RequestParam Map<String, String> result){
        model.addAttribute("result", result);
        return "vnpay/resultPayment";
    }


    //Security problem, validate request send from vnpay server
    @GetMapping("/vnp_ipn")
    public ResponseEntity<Void> ipnHandle(@RequestParam Map<String, String> params){


        System.out.println("Return param from IPN URL: " + params);
        try {
            vnPayIpnHandler.process(params);
            // Return 200 OK if processed successfully
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Log the exception and return 400 Bad Request
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }



//    public String getClientIp(HttpServletRequest request) {
//        // Use VnpayUtil to get client IP address
//        String clientIp = VnpayUtil.getIpAddress(request);
//        return "Client IP Address: " + clientIp;
//    }


}
