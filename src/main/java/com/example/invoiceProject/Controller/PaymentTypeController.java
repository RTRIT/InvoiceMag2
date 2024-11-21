package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.PaymentType;
import com.example.invoiceProject.Service.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/PaymentTypes")
public class PaymentTypeController {
    @Autowired(required = false)
    private PaymentTypeService paymentTypeService;

    @GetMapping("/{paymentType_id}")
    public ResponseEntity<PaymentType> getPaymentTypeById(@PathVariable("paymentType_id") Long paymentTypeId) {
        Optional<PaymentType> optionalPaymentType = paymentTypeService.findPaymentTypeById(paymentTypeId);

        return optionalPaymentType
                .map(paymentType -> ResponseEntity.ok(paymentType)) // Nếu có giá trị, trả về mã 200 và đối tượng
                .orElseGet(() -> ResponseEntity.notFound().build()); // Nếu không có giá trị, trả về mã 404
    }

    @GetMapping
    public ResponseEntity<List<PaymentType>> getAllPaymentTypes() {
        List<PaymentType> paymentTypes = paymentTypeService.getAllPaymentTypes();
        return ResponseEntity.ok(paymentTypes);
    }

    @PutMapping("/{PaymentType_id}")
    public ResponseEntity<String> updatePaymentType(@RequestBody PaymentType PaymentType) {
        paymentTypeService.saveOrUpdatePaymentType(PaymentType);
        return ResponseEntity.ok("PaymentType updated successfully");
    }

    @DeleteMapping("/{PaymentType_id}")
    public ResponseEntity<String> deletePaymentType(@PathVariable Long id) {
        paymentTypeService.deletePaymentTypeById(id);
        return ResponseEntity.ok("PaymentType deleted successfully");
    }
    
}
