package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/privileges")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @GetMapping("")
    public List<Privilege> getList(){
        return privilegeService.getList();
    }

    //Add Privilege
    @PostMapping("/new")
    public void addPrivilege(@RequestBody Privilege privilege){
        privilegeService.addPrivilege(privilege.getPrivilegeName());
    }

    //Delete Privilege
    @DeleteMapping("/{id}/delete")
    public void deletePrivilege(@PathVariable Long id){
        privilegeService.deletePrivilege(id);
    }

}
