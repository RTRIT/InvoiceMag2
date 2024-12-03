package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.Product;
import com.example.invoiceProject.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProductsByKeyword(String keyword) {
        return productRepository.findByKeyword(keyword);
    }

    //get by id
    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    } 

    // create
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    //update
    public Product updateProduct(Product product, UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product1 = productOptional.get();
            product1.setName(product.getName());
            product1.setCode(product.getCode());
            product1.setUnit(product.getUnit());
            product1.setPrice(product.getPrice());
            product1.setTax(product.getTax());
            product1.setCurrency(product.getCurrency());
            product1.setDescription(product.getDescription());
            return productRepository.save(product1);
        }
        return null;
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteProduct(id);
    }
}
