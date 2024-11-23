package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsBynameDepartment(String name);

    @Query(value = "SELECT d FROM Department d WHERE d.nameDepartment = :department")
    Department findByName(@Param("department") String department);


    Department findByNameDepartment(String departmentName);
}
