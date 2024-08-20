package com.example.invoiceProject.Service;

import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Exception.ResourceNotFoundException;
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
    RoleRepository roleRepository;

    public List<Role> getList() {
        return roleRepository.findAll();
    }

    @Transactional
    public void addRole(String name, List<Privilege> privileges){
        //To check the same role name is register
        try{
            roleRepository.addNewRole(name);
            Long id = roleRepository.getIdByRoleName(name); //
            if(privileges !=  null){
                for (Privilege privilege:privileges){
                    roleRepository.addPrivilegeToRole(id, privilege.getId());
                }
            }
        }catch (Exception ex) {
            // Log lỗi và gửi thông báo cho người dùng
            System.out.println("Transaction bị rollback do lỗi cơ sở dữ liệu.");
            // Bạn có thể ném lại một ngoại lệ tùy chỉnh để thông báo lỗi lên tầng trên
            throw new CustomException(name+" is existed already!!");
        }
    }
    @Transactional
    public void deleteRole(Long id){
        // Delete row that reference to this role_id in privilege_detail table
        //
        roleRepository.deleteRole(id);
    }

    public Role getRoleByName(String role) {return roleRepository.findByRoleName(role);}
}
