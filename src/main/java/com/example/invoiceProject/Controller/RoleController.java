package com.example.invoiceProject.Controller;


import com.example.invoiceProject.DTO.requests.RoleRequest;
import com.example.invoiceProject.DTO.response.ApiResponse;
import com.example.invoiceProject.DTO.response.RoleResponse;
import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Service.PrivilegeService;
import com.example.invoiceProject.Service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/role")
public class
RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private ModelMapper mapper;



    @GetMapping("/list")
    public String rolesList(ModelMap model){
        model.addAttribute("roles", roleService.getAll());
        return "role/index";
    }

//

    @GetMapping("/new")
    public String create(ModelMap model) {
        RoleRequest roleRequest = new RoleRequest();
        model.addAttribute("role", roleRequest);
        model.addAttribute("privileges", privilegeService.getList());
        return "role/new";
    }

    @PostMapping("/new")
    public String create(ModelMap model, @ModelAttribute("user") RoleRequest request) {
        try{
            RoleResponse roleResponse = roleService.create(request);
            model.addAttribute("message", "Registration successful!");
            return "redirect:/role/list";
        }catch (Exception ex){
            model.addAttribute("error", "Failed to register user: " + ex.getMessage());
            return "new";
        }
    }

    @PostMapping("update/{role}")
    ApiResponse<RoleResponse> update(@PathVariable("role") Long role, @RequestBody RoleRequest request) {
        roleService.update(role, request);
        return  ApiResponse.<RoleResponse>builder()
                .build();
    }

    @PostMapping("/delete/{role}")
    ApiResponse<Void> delete(@PathVariable("role") Long role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder().build();
    }
//
//    @GetMapping
//    ApiResponse<List<RoleResponse>> getAll() {
//        return ApiResponse.<List<RoleResponse>>builder()
//                .result(roleService.getAll())
//                .build();
//    }
//

//




}
