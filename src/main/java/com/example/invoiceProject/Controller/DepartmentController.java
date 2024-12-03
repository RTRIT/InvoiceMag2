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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/department")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;



    @GetMapping("/list")
    public String getList(ModelMap model){
        model.addAttribute("departments", departmentService.getList());
        return "department/index";
    }

    @GetMapping("/new")
    public String add(ModelMap model){
        DepartmentRequest departmentRequest = new DepartmentRequest();
        model.addAttribute("department", departmentRequest);

        return "department/new";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute DepartmentRequest request){
        System.out.println(request);
        departmentService.addDepart(request);
        return "department/new";
    }


    @DeleteMapping("/department/delete/{nameDepartment}")
    public void delete(@PathVariable String nameDepartment){
        departmentService.deleteDepartment(nameDepartment);
    }
}
