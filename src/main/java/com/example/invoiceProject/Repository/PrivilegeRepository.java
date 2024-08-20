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
    @Query(value = "INSERT INTO privilege (privilege_name, privilege_desc) VALUES ( :name, :desc )", nativeQuery = true)
    void addPrivilege(@Param("name") String name, @Param("desc") String desc);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM privilege WHERE id = :id", nativeQuery = true)
    void deletePrivilege(@Param("id") Long id);

    @Query(value = "SELECT id FROM Privilege WHERE privilegeName = :name")
    Long getIdByName(String name);
}
