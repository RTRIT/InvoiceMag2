package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.Product;
import com.example.invoiceProject.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
//        Product product = productService.getProductById(id);
//        return ResponseEntity.ok(product);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts() {
//        List<Product> products = productService.getAllProducts();
//        return ResponseEntity.ok(products);
//    }
//
//    @PostMapping
//    public ResponseEntity<String> createProduct(@RequestBody Product product) {
//        productService.createProduct(product);
//        return ResponseEntity.ok("Product created successfully");
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateProduct(@RequestBody Product product, @PathVariable Long id) {
//        productService.updateProduct(product, id);
//        return ResponseEntity.ok("Product updated successfully");
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.ok("Product deleted successfully");
//    }
}
