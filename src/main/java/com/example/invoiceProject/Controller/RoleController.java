package com.example.invoiceProject.Controller;


import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class
RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public List<Role> getList(){
        return roleService.getList();
    }


    @PostMapping("/new")
    public ResponseEntity<String> addRole(@RequestBody Role role){
        String roleName = role.getRoleName();
        List<Privilege> listPriv = role.getPrivileges();

        roleService.addRole(roleName, listPriv);
        return ResponseEntity.ok("Role add successfully");
    }

    //Delete Role
    @DeleteMapping("/{id}/delete")
    public void deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);
    }

    //Update Role
    @PutMapping("/{id}/update")
    public void updateRole(){

    }



}
