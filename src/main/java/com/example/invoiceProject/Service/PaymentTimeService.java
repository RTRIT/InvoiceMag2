package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.PaymentTime;
import com.example.invoiceProject.Repository.PaymentTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentTimeService {
    @Autowired
    private PaymentTimeRepository paymentTimeRepository;

    // Tìm PaymentTime theo mã PaymentTimeCode
    public Optional<PaymentTime> findByPaymentTimeCode(String paymentTimeCode) {
        return paymentTimeRepository.findByPaymentTimeCode(paymentTimeCode);
    }

    // Tìm tất cả PaymentTime theo danh sách mã PaymentTimeCode
    public List<PaymentTime> findAllByPaymentTimeCodeIn(List<String> paymentTimeCodes) {
        return paymentTimeRepository.findAllByPaymentTimeCodeIn(paymentTimeCodes);
    }

    // Lưu hoặc cập nhật một PaymentTime
    public PaymentTime saveOrUpdatePaymentTime(PaymentTime paymentTime) {
        return paymentTimeRepository.save(paymentTime);
    }

    // Xóa một PaymentTime theo ID
    public void deletePaymentTimeById(UUID id) {
        paymentTimeRepository.deleteById(id);
    }

    // Lấy tất cả PaymentTime
    public List<PaymentTime> getAllPaymentTimes() {
        return paymentTimeRepository.findAll();
    }

    // Tìm PaymentTime theo ID
    public Optional<PaymentTime> findPaymentTimeById(UUID id) {
        return paymentTimeRepository.findById(id);
    }
}
