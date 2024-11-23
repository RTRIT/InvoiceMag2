package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentReponsitory extends JpaRepository<Role, Long> {

    @Query(value = "SELECT r FROM Department r WHERE r.nameDepartment = :role")
    Role findByRoleName(@Param("role") String role);

    @Query(value = "SELECT * FROM role", nativeQuery = true)
    List<Role> getList();

    @Modifying
    @Query("DELETE FROM Role r WHERE r.id = :id")
    void deleteRole(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Role r SET r.privileges = :privilege WHERE r.roleName=:name ")
    void updateRole(@Param("privilege")Privilege privilege, @Param("name")String name);

    @Override
    boolean existsById(Long aLong);

    boolean existsByRoleName(String roleName);
}
