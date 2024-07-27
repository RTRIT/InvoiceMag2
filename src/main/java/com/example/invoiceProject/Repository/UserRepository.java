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
    @Query(value = "SELECT * FROM user u WHERE u.username= :username and u.password = :password", nativeQuery = true)
    User authenticate(@Param("username") String username, @Param("password") String password);

    //Register User
    @Transactional
    @Modifying
    @Query(value= "INSERT INTO user (username, password) VALUES (:username, :password)", nativeQuery = true)
    void register(@Param("username") String username, @Param("password") String password);

    //Get user by username
    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    User getUserByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET username = :username, password = :password, full_name = :fullName WHERE id = :id ", nativeQuery = true)
    void updateUserById(@Param("username") String username, @Param("password") String password, @Param("fullName") String fullName, @Param("id") Long id);

}
