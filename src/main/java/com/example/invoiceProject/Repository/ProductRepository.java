package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
        List<Product> findAll();
}
    // Get Product by ID
//    @Query(value = "SELECT * FROM Product WHERE id = :id", nativeQuery = true)
//    Product getProductById(@Param("id") Long id);
//
//    // Get all Products
//    @Query(value = "SELECT * FROM Product", nativeQuery = true)
//    List<Product> getAllProducts();

    // Create Product
//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO Product (name, code, price, tax, gross_price, currency, description) VALUES (:name, :code, :price, :tax, :grossPrice, :currency, :description)", nativeQuery = true)
//    void createProduct(@Param("name") String name,
//                       @Param("code") String code,
//                       @Param("price") Double price,
//                       @Param("tax") Double tax,
//                       @Param("grossPrice") Double grossPrice,
//                       @Param("currency") String currency,
//                       @Param("description") String description);
//
//    // Update Product by ID
//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE Product SET name = :name, code = :code, price = :price, tax = :tax, " +
//            "gross_price = :grossPrice, currency = :currency, description = :description " +
//            "WHERE id = :id", nativeQuery = true)
//    void updateProduct(@Param("id") Long id,
//                       @Param("name") String name,
//                       @Param("code") String code,
//                       @Param("price") Double price,
//                       @Param("tax") Double tax,
//                       @Param("grossPrice") Double grossPrice,
//                       @Param("currency") String currency,
//                       @Param("description") String description);
//
//    // Delete Product by ID
//    @Transactional
//    @Modifying
//    @Query(value = "DELETE FROM Product WHERE id = :id", nativeQuery = true)
//    void deleteProduct(@Param("id") Long id);


