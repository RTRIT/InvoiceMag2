package com.example.invoiceProject.Repository;
import com.example.invoiceProject.Model.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.invoiceProject.Model.Money;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoneyRepository extends JpaRepository<Money, Long> {
        // Các phương thức tùy chỉnh có thể thêm ở đây nếu cần
        Optional<Money> findByMoneyCode(String moneyCode);
        List<Money> findAllByMoneyCodeIn(List<String> moneyCodes);
}

