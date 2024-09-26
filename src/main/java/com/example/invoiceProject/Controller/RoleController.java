package com.example.invoiceProject.Controller;


import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class
RoleController {
    @Autowired
    private RoleService roleService;


    //Get list role
    @GetMapping("")
    public List<Role>  roleList(){
        return roleService.getList();
    }


    //Add
    @PostMapping("/new")
    public ResponseEntity<String> addRole(@RequestBody Role role){
        roleService.addRole(role);
        return ResponseEntity.ok("Role add successfully");
    }


    @DeleteMapping("/{id}/delete")
    public void deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);
    }

    //Update Role
    @PutMapping("/{id}/update")
    public ResponseEntity updateRole(@RequestBody Role role){
        roleService.updateRole(role);
        try{
            roleService.updateRole(role);
            return ResponseEntity.ok("Role updated successfully!!");
        }catch (CustomException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }





}
