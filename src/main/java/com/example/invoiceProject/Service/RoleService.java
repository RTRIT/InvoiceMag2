package com.example.invoiceProject.Service;

import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> getList() {
        return roleRepository.findAll();
    }

    public void addRole(String name){
        roleRepository.addRole(name);
    }

    public void deleteRole(Long id){
        roleRepository.deleteRole(id);
    }

    public Role getRoleByName(String role) {return roleRepository.findByRoleName(role);}
}
