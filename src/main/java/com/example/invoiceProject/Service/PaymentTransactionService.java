package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.PaymentTransaction;
import com.example.invoiceProject.Repository.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class PaymentTransactionService {

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    public void save(Map<String, String> params){
        System.out.println("This is the save method of PaymentTransactionService: "+params);
        PaymentTransaction payTrans = new PaymentTransaction();
        payTrans.setTxnRef(params.get("vnp_TxnRef"));
        payTrans.setVnpBankTransNo(params.get("vnp_BankTranNo"));
        payTrans.setVnpBankCode(params.get("vnp_BankCode"));
        payTrans.setRespCode(params.get("vnp_ResponseCode"));
        payTrans.setOrderInfo(params.get("vnp_OrderInfo"));

        //Get invoice id from order info
        String sequenceNo = params.get("vnp_OrderInfo").split(":")[1].strip().trim();
        payTrans.setInvoiceId(sequenceNo);

        String payDate = params.get("vnp_PayDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        payTrans.setVnpPayDate(LocalDateTime.parse(payDate, formatter));
        System.out.println("This is pay trans: "+payTrans);
        paymentTransactionRepository.save(payTrans);


    }
}
