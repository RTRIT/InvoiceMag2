package com.example.invoiceProject.Service;

import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void addRole(Role newRole) {
        if (roleRepository.findByRoleName(newRole.getRoleName()) != null) {
            throw new CustomException("This role already exists");
        }
        if (newRole.getPrivileges() == null || newRole.getPrivileges().isEmpty()) {
            throw new CustomException("Please choose privileges for the role");
        }
        roleRepository.save(newRole);
    }

    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    //update multiple privileges in a role
    @Transactional
    public void updateRole(Role role){
        Role existingRole = roleRepository.findByRoleName(role.getRoleName());
        if(existingRole==null){
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        if(role.getPrivileges()==null || role.getPrivileges().isEmpty()){
            throw new AppException(ErrorCode.EMPTY_PRIVILEGE);
        }

        existingRole.setPrivileges(role.getPrivileges());
        roleRepository.save(existingRole);

    }


    public List<Role> getList() {
        return roleRepository.getList();
    }
}
