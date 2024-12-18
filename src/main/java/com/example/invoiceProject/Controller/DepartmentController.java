package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.requests.DepartmentRequest;
import com.example.invoiceProject.DTO.response.ApiResponse;
import com.example.invoiceProject.DTO.response.DepartmentResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Model.Department;
import com.example.invoiceProject.Repository.DepartmentRepository;
import com.example.invoiceProject.Service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/department")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;



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
    public String add(@ModelAttribute DepartmentRequest request,
                      ModelMap model,
                      RedirectAttributes redirectAttributes){
        try {
            departmentService.addDepart(request);
            redirectAttributes.addFlashAttribute("successMessage", "Create new Department successfully!");
            return "redirect:/department/new";
        } catch (AppException e) {
            // Add error details to the ModelMap
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("department", request); // To keep user inputs
            return "department/new"; // Stay on the form page to display the error
        }
    }

    @GetMapping("/update")
    public String udpate(@RequestParam("nameDepartment") String nameDepartment
            ,ModelMap model){
//        System.out.println("Get into departmentController"+ nameDepartment);
        Department department = departmentRepository.findByName(nameDepartment);
        System.out.println("Getmapping this is address: "+department.getAddress().getCity());
//        System.out.println(department);
        model.addAttribute("department", department);
//        return "department/update";
        return "department/update";

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("department") Department department,
                         RedirectAttributes redirectAttributes,
                         ModelMap model){

        departmentRepository.save(department);
        System.out.println(department.getAddress().getCity());

        redirectAttributes.addFlashAttribute("successMessage", "Department updated successfully.");
        model.addAttribute("successMessage", "Department updated successfully.");
        return "redirect:/department/update?nameDepartment=" + department.getNameDepartment();

    }


    @PostMapping("/delete")
    public String delete(@RequestParam("nameDepartment") String nameDepartment,
                         ModelMap model,
                         RedirectAttributes redirectAttributes){
        System.out.println("Get into departmentController: "+nameDepartment);
        try{
            departmentService.deleteDepartment(nameDepartment);
            redirectAttributes.addFlashAttribute("successMessage", "Delete Department successfully!");
            return "redirect:/department/list";
        }catch (AppException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "department/list";
        }
    }
}
