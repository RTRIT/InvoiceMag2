package com.example.invoiceProject.Controller;


import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Void> addRole(@RequestBody Role role){
        System.out.print(role.getPrivileges().size());

        roleService.addRole(role.getRoleName(), role.getPrivileges());

        return ResponseEntity.ok().build();
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
