package com.example.invoiceProject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import com.example.invoiceProject.Model.Type;
@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    
    // Get type by typeID
    @Query(value = "SELECT * FROM type WHERE type_id = :type_id", nativeQuery = true)
    Type getTypeById(@Param("type_id") Long type_id);

    // Get all types
    @Query(value = "SELECT * FROM type", nativeQuery = true)
    List<Type> getAllTypes();

    // create type
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO type (name, description) VALUES (:name, :description)", nativeQuery = true)
    void createType(@Param("name") String name, @Param("description") String description);

    //update type by typeID
    @Transactional
    @Modifying
    @Query(value = "UPDATE type SET name = :name, description = :description WHERE type_id = :type_id", nativeQuery = true)
    void updateType(@Param("type_id") Long type_id, @Param("name") String name, @Param("description") String description);

    //delete type
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM type WHERE type_id = :type_id", nativeQuery = true)
    void deleteType(@Param("type_id") Long type_id);
 
}
