package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository; // Provide set of methods for performing CRUD operation on "User" entity
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository; // Specify this interface is a repository


//Create repository interface for handling database operations
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //Authenicate login
    @Query(value = "SELECT * FROM user u WHERE u.email= :email and u.password = :password", nativeQuery = true)
    User authenticate(@Param("email") String email, @Param("password") String password);

    //Register User
    @Transactional
    @Modifying
    @Query(value= "INSERT INTO user (email, password) VALUES (:email, :password)", nativeQuery = true)
    void register(@Param("email") String email, @Param("password") String password);

    //Get user by email
    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    User getUserByUsername(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET email = :email, password = :password", nativeQuery = true)
    void updateUserById(@Param("email") String email, @Param("password") String password);

}
