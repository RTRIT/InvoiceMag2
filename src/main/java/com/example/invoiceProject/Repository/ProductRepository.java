package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.PaymentType;
import com.example.invoiceProject.Model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAll();

    Optional<Product> findById(UUID id);

    Optional<Product> findByCode(String code);

    List<Product> findAllByIdIn(List<UUID> id);

    //search by name or code
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.code) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> findByKeyword(@Param("keyword") String keyword);

   // Get all Products
   @Query(value = "SELECT * FROM Product", nativeQuery = true)
   List<Product> getAllProducts();

    //Create Product
   @Transactional
   @Modifying
   @Query(value = "INSERT INTO Product (name, code,unit, price, tax, currency, description) VALUES (:name, :code,:unit :price, :tax, :currency, :description)", nativeQuery = true)
   void createProduct(@Param("name") String name,
                      @Param("code") String code,
                      @Param("unit") String unit,
                      @Param("price") Double price,
                      @Param("tax") Double tax,
                      @Param("currency") String currency,
                      @Param("description") String description);

   // Update Product by ID
    @Transactional
    @Modifying
    @Query(value = "UPDATE Product SET name = :name, code = :code, unit = :unit, price = :price, tax = :tax, currency = :currency, description = :description WHERE id = :id", nativeQuery = true)
    void updateProduct(@Param("name") String name,
                       @Param("code") String code,
                       @Param("unit") String unit,
                       @Param("price") Double price,
                       @Param("tax") Double tax,
                       @Param("currency") String currency,
                       @Param("description") String description,
                       @Param("id") UUID id);

    // Delete Product by ID
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Product WHERE id = :id", nativeQuery = true)
    void deleteProduct(@Param("id") UUID id);
    

}
