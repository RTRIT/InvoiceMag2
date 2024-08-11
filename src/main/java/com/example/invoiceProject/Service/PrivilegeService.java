package com.example.invoiceProject.Service;


import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {
    @Autowired
    private PrivilegeRepository privilegeRepository;

    public List<Privilege> getList(){
        return privilegeRepository.findAll();
    }

    public void addPrivilege(String name){
        privilegeRepository.addPrivilege(name);
    }

    public void deletePrivilege(Long id){
        privilegeRepository.deletePrivilege(id);
    }
}
