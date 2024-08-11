package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO role (role_name) VALUES(:name)", nativeQuery = true)
    void addRole(@Param("name") String name);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM role WHERE id = :id", nativeQuery = true)
    void deleteRole(@Param("id") Long id);


    @Query(value = "SELECT * FROM role WHERE role_name = :role", nativeQuery = true)
    Role findByRoleName(@Param("role") String role);
}
