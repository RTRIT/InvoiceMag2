package com.example.invoiceProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import org.springframework.web.bind.annotation.*;
        import com.example.invoiceProject.Model.Money;
import com.example.invoiceProject.Service.MoneyService;

import java.util.Optional;


@RestController
@RequestMapping("/api/money")
public class MoneyController {

    @Autowired
    private MoneyService moneyService;

    // Endpoint để tạo mới Money
    @PostMapping
    public ResponseEntity<Money> createMoney(@RequestBody Money money) {
        try {
            Money createdMoney = moneyService.createMoney(money);
            return ResponseEntity.ok(createdMoney);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint để cập nhật Money
    @PutMapping("/{id}")
    public ResponseEntity<Money> updateMoney(@PathVariable Long id, @RequestBody Money money) {
        try {
            Money updatedMoney = moneyService.updateMoney(id, money);
            return ResponseEntity.ok(updatedMoney);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint để lấy Money theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Money> getMoneyById(@PathVariable Long id) {
        Optional<Money> money = moneyService.getMoneyById(id);
        return money.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
