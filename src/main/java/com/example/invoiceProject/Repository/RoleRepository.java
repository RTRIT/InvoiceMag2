package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO role (role_name) VALUES(:name)", nativeQuery = true)
//    void addNewRole(@Param("name") String name);

//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO privilege_detail (role_id, privilege_id) VALUES (:roleId, :privilegeId) ", nativeQuery = true)
//    void addPrivilegeToRole(@Param("roleId") Long roleId, @Param("privilegeId") Long privilegeId);

    @Query(value = "SELECT r FROM Role r WHERE r.roleName = :role")
    Role findByRoleName(@Param("role") String role);


    @Modifying
    @Query("DELETE FROM Role r WHERE r.id = :id")
    void deleteRole(@Param("id") Long id);





}
