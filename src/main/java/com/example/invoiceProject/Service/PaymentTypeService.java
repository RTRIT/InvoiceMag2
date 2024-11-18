package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.PaymentType;
import com.example.invoiceProject.Repository.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentTypeService {
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    // Tìm PaymentType theo mã PaymentTypeCode
    public Optional<PaymentType> findByPaymentTypeCode(String paymentTypeCode) {
        return paymentTypeRepository.findByPaymentTypeCode(paymentTypeCode);
    }

    // Tìm tất cả PaymentType theo danh sách mã PaymentTypeCode
    public List<PaymentType> findAllByPaymentTypeCodeIn(List<String> paymentTypeCodes) {
        return paymentTypeRepository.findAllByPaymentTypeCodeIn(paymentTypeCodes);
    }

    // Lưu hoặc cập nhật một PaymentType
    public PaymentType saveOrUpdatePaymentType(PaymentType paymentType) {
        return paymentTypeRepository.save(paymentType);
    }

    // Xóa một PaymentType theo ID
    public void deletePaymentTypeById(UUID id) {
        paymentTypeRepository.deleteById(id);
    }

    // Lấy tất cả PaymentType
    public List<PaymentType> getAllPaymentTypes() {
        return paymentTypeRepository.findAll();
    }

    // Tìm PaymentType theo ID
    public Optional<PaymentType> findPaymentTypeById(UUID id) {
        return paymentTypeRepository.findById(id);
    }
}
