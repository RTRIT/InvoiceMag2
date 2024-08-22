package com.example.invoiceProject.Service;


import com.example.invoiceProject.Exception.CustomException;
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

    public void addPrivilege(String name, String desc){
        //Check field filled up already
        if(name==null || desc==null){
            throw new CustomException("Please fill in all field");
        }
        //Create Privilege
        Privilege newPrivilege = new Privilege(name, desc);
        privilegeRepository.save(newPrivilege);
    }

    public void deletePrivilege(Long id){
        privilegeRepository.deleteById(id);
    }
}
