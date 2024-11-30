package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.Product;
import com.example.invoiceProject.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

//    public Product getProductById(Long id) {
//        return productRepository.getProductById(id);
//    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

//    public void createProduct(Product product) {
//        Double grossPrice1 = product.getPrice() + product.getTax();
//        productRepository.createProduct(product.getName(), product.getCode(), product.getPrice(), product.getTax(), grossPrice1, product.getCurrency(), product.getDescription());
//    }
//
//    public void updateProduct(Product product, Long id) {
//
//        Double grossPrice1 = product.getPrice() + product.getTax();
//        product.setGrossPrice(grossPrice1);
//        productRepository.updateProduct(id,product.getName(), product.getCode(), product.getPrice(), product.getTax(), product.getGrossPrice(), product.getCurrency(), product.getDescription());
//    }
//
//    public void deleteProduct(Long id) {
//        productRepository.deleteProduct(id);
//    }
}

