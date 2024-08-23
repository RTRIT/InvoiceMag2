package com.example.invoiceProject.Service;


import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeService {
    @Autowired
    private PrivilegeRepository privilegeRepository;

    public List<Privilege> getList(){
        return privilegeRepository.findAll();
    }

    public void addPrivilege(Privilege newPrivilege){
        privilegeRepository.save(newPrivilege);

    }

    public void deletePrivilege(Long id){
        privilegeRepository.deleteById(id);
    }
}
