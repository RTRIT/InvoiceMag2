package com.example.invoiceProject.Service;

import com.example.invoiceProject.DTO.requests.DepartmentRequest;
import com.example.invoiceProject.DTO.response.DepartmentResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Department;
import com.example.invoiceProject.Repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentResponse addDepart (DepartmentRequest request){
        Department department = mapper.map(request, Department.class);
        System.out.println("2. "+ department.getNameDepartment());
        departmentRepository.save(department);
        return mapper.map(department, DepartmentResponse.class);
    }

    public List<Department> getList(){
        return departmentRepository.findAll();
    }

    public Department findByName(String department){
        return departmentRepository.findByName(department);
    }

    public void deleteDepartment(String departmentName){
        Department department = departmentRepository.findByName(departmentName);

        departmentRepository.delete(department);
    }

}
