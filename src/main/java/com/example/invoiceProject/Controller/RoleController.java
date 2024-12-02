package com.example.invoiceProject.Controller;


import com.example.invoiceProject.DTO.requests.RoleRequest;
import com.example.invoiceProject.DTO.response.ApiResponse;
import com.example.invoiceProject.DTO.response.PrivilegeResponse;
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

    @GetMapping("update/{role}")
    public String update(ModelMap model, @PathVariable("role") Long id){
        RoleResponse role = roleService.getRoleById(id);

        // Add the role itself as a model attribute
        model.addAttribute("role", role);

        // Add all privileges and selected privileges to the model
        model.addAttribute("privileges", privilegeService.getList());
        model.addAttribute("role_privileges", role.getPrivileges().stream()
                .map(Privilege::getId)
                .collect(Collectors.toSet())); // Collect the selected privilege IDs

        return "role/update";
    }

    @PostMapping("update/{role}")
    public String update(@PathVariable("role") Long role, @ModelAttribute("role") RoleRequest request) {
        System.out.println(request);
        roleService.update(role, request);
        return "redirect:/role/list";
    }



    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        roleService.delete(id);
        return "redirect:/role/list";
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
