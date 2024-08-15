package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository; // Provide set of methods for performing CRUD operation on "User" entity
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; // Specify this interface is a repository

import java.util.List;


//Create repository interface for handling database operations
@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    @Query(value = "SELECT * FROM user;", nativeQuery = true)
    List<User> getListUser();

    //Authenicate login
    @Query(value = "SELECT * FROM user u WHERE u.email= :email and u.password = :password", nativeQuery = true)
    User authenticate(@Param("email") String email, @Param("password") String password);

    //Register User
    @Transactional
    @Modifying
    @Query(value= "INSERT INTO user (email, password, role_id) VALUES (:email, :password, :role)", nativeQuery = true)
    void register(@Param("email") String email, @Param("password") String password, @Param("role") Long role);

    //Get user by email
    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    User getUserByUsername(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET email = :email, password = :password, role_id = :id", nativeQuery = true)
    void updateUserById(@Param("email") String email, @Param("password") String password, int id);

    @Modifying
    @Query(value = "DELETE FROM user WHERE id=:id", nativeQuery = true)
    void deleteUserById(@Param("id") Long id);

}
