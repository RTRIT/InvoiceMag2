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
import java.util.Optional;


//Create repository interface for handling database operations
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    //Authenticate login
    @Query(value = "SELECT u.email,u.password FROM User u WHERE u.email= :email and u.password = :password")
    User authenticate(@Param("email") String email, @Param("password") String password);


    //Get user by email
    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);


    //Edit user by id
    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET email = :email, password = :password, role_id = :id")
    void updateUserById(@Param("email") String email, @Param("password") String password, int id);


    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    User getUserByEmail(@Param("email") String email);

    @Query(value = "SELECT COUNT(u)>0 FROM User u WHERE u.email = :email")
    boolean existUser(@Param("email") String email);

}
