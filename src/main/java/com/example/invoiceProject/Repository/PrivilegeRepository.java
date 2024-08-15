package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Privilege;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO privilege (privilege_name) VALUES ( :name )", nativeQuery = true)
    void addPrivilege(@Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM privilege WHERE id = :id", nativeQuery = true)
    void deletePrivilege(@Param("id") Long id);

}
