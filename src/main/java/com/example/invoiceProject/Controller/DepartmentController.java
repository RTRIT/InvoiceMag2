package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.requests.DepartmentRequest;
import com.example.invoiceProject.DTO.response.ApiResponse;
import com.example.invoiceProject.DTO.response.DepartmentResponse;
import com.example.invoiceProject.Model.Department;
import com.example.invoiceProject.Service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/department/add")
    ApiResponse<DepartmentResponse> addDepartment(@RequestBody DepartmentRequest request){
        System.out.println("1. "+request);
        DepartmentResponse departmentResponse = departmentService.addDepart(request);
        System.out.println("3. "+departmentResponse)
        ;
        return ApiResponse.<DepartmentResponse>builder()
                .code(1000)
                .message("SUCCESS")
                .result(departmentResponse)
                .build();
    }

    @GetMapping("/department")
    public List<Department> getList(){
        return departmentService.getList();
    }

    @DeleteMapping("/department/delete/{nameDepartment}")
    public void delete(@PathVariable String nameDepartment){
        departmentService.deleteDepartment(nameDepartment);
    }
}
