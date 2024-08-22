package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.PaymentTime;
import com.example.invoiceProject.Model.PaymentType;
import com.example.invoiceProject.Service.PaymentTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/PaymentTimes")
public class PaymentTimeController {
    @Autowired
    private PaymentTimeService paymentTimeService;


    @GetMapping("/{paymentTime_id}")
    public ResponseEntity<PaymentTime> getPaymentTimeById(@PathVariable("paymentTime_id") Long paymentTimeId) {
        Optional<PaymentTime> optionalPaymentTime = paymentTimeService.findPaymentTimeById(paymentTimeId);

        return optionalPaymentTime
                .map(paymentTime -> ResponseEntity.ok(paymentTime)) // Nếu có giá trị, trả về mã 200 và đối tượng
                .orElseGet(() -> ResponseEntity.notFound().build()); // Nếu không có giá trị, trả về mã 404
    }

    @GetMapping
    public ResponseEntity<List<PaymentTime>> getAllPaymentTypes() {
        List<PaymentTime> paymentTimes = paymentTimeService.getAllPaymentTimes();
        return ResponseEntity.ok(paymentTimes);
    }


    @PutMapping("/{PaymentTime_id}")
    public ResponseEntity<String> updatePaymentTime(@RequestBody PaymentTime PaymentTime) {
        paymentTimeService.saveOrUpdatePaymentTime(PaymentTime);
        return ResponseEntity.ok("PaymentTime updated successfully");
    }

    @DeleteMapping("/{PaymentTime_id}")
    public ResponseEntity<String> deletePaymentTime(@PathVariable Long id) {
        paymentTimeService.deletePaymentTimeById(id);
        return ResponseEntity.ok("PaymentTime deleted successfully");
    }

}
