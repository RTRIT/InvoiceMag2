package com.example.invoiceProject.Service;

import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Exception.ResourceNotFoundException;
import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Repository.PrivilegeRepository;
import com.example.invoiceProject.Repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PrivilegeRepository privilegeRepository;



    @Transactional
    public void addRole(String name, List<Privilege> privilegeList){

        //Check if role exist
        if(roleRepository.findByRoleName(name)!=null){
            throw new CustomException("This role existed already");
        }
        if(privilegeList==null){
            throw new CustomException("please choose privilege for role");
        }

        //Create new Role
        Role newRole = new Role();
        newRole.setRoleName(name);
        newRole.setPrivileges(privilegeList);

        roleRepository.save(newRole);

    }
    @Transactional
    public void deleteRole(Long id){
        roleRepository.deleteById(id);
    }

    public List<Role> getList() {
        return roleRepository.findAll();
    }

}
