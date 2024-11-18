package com.example.invoiceProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.invoiceProject.Model.Money;
import com.example.invoiceProject.Repository.MoneyRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class MoneyService {

    @Autowired
    private MoneyRepository moneyRepository;

    // Phương thức để tạo mới Money
    public Money createMoney(Money money) {
        if (money.getId() != null) {
            throw new IllegalArgumentException("Cannot create Money with an existing ID");
        }
        return moneyRepository.save(money);
    }

    // Phương thức để cập nhật Money
    public Money updateMoney(UUID id, Money money) {
        if (!moneyRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot update Money with non-existing ID");
        }
        money.setId(id); // Đảm bảo ID được thiết lập
        return moneyRepository.save(money);
    }

    // Phương thức để tìm Money theo ID
    public Optional<Money> getMoneyById(UUID id) {
        return moneyRepository.findById(id);
    }

    public Optional<Money> findMoneyById(UUID id) {
        return moneyRepository.findById(id);
    }
}
