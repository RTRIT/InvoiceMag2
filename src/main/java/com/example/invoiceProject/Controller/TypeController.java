package com.example.invoiceProject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.invoiceProject.Model.Type;
import com.example.invoiceProject.Service.TypeService;

@RestController
@RequestMapping("/api/types")
public class TypeController {
    @Autowired
    TypeService typeService;

    @GetMapping("/{type_id}")
    public ResponseEntity<Type> getTypeById(@PathVariable Long type_id) {
        Type type = typeService.getTypeById(type_id);
        return ResponseEntity.ok(type);
    }

    @GetMapping
    public ResponseEntity<List<Type>> getAllTypes() {
        List<Type> types = typeService.getAllTypes();
        return ResponseEntity.ok(types);
    }

    @PostMapping
    public ResponseEntity<String> createType(@RequestBody Type type) {
        typeService.createType(type);
        return ResponseEntity.ok("Type created successfully");
    }

    @PutMapping("/{type_id}")
    public ResponseEntity<String> updateType(@RequestBody Type type, @PathVariable Long type_id) {
        typeService.updateType(type, type_id);
        return ResponseEntity.ok("Type updated successfully");
    }

    @DeleteMapping("/{type_id}")
    public ResponseEntity<String> deleteType(@PathVariable Long type_id) {
        typeService.deleteType(type_id);
        return ResponseEntity.ok("Type deleted successfully");
    }
}
