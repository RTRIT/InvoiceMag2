package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT r FROM Role r WHERE r.roleName = :role")
    Role findByRoleName(@Param("role") String role);


    @Modifying
    @Query("DELETE FROM Role r WHERE r.id = :id")
    void deleteRole(@Param("id") Long id);





}
