//package com.example.invoiceProject.Service;
//
//import com.example.invoiceProject.Exception.AppException;
//import com.example.invoiceProject.Exception.CustomException;
//import com.example.invoiceProject.Exception.ErrorCode;
//import com.example.invoiceProject.Model.Privilege;
//import com.example.invoiceProject.Model.Role;
//import com.example.invoiceProject.Repository.RoleRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;

//@Service
//public class RoleService {
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Transactional
//    public void addRole(Role newRole) {
//        if (roleRepository.findByRoleName(newRole.getRoleName()) != null) {
//            throw new CustomException("This role already exists");
//        }
//        if (newRole.getPrivileges() == null || newRole.getPrivileges().isEmpty()) {
//            throw new CustomException("Please choose privileges for the role");
//        }
//        roleRepository.save(newRole);
//    }
//
//    @Transactional
//    public void deleteRole(Long id) {
//        roleRepository.deleteById(id);
//    }
//
//    //update multiple privileges in a role
//    @Transactional
//    public void updateRole(Role role){
//        Role existingRole = roleRepository.findByRoleName(role.getRoleName());
//        if(existingRole==null){
//            throw new AppException(ErrorCode.ROLE_EXISTED);
//        }
//        if(role.getPrivileges()==null || role.getPrivileges().isEmpty()){
//            throw new AppException(ErrorCode.EMPTY_PRIVILEGE);
//        }
//
//        existingRole.setPrivileges(role.getPrivileges());
//        roleRepository.save(existingRole);
//
//    }
//
//
//    public List<Role> getList() {
//        return roleRepository.getList();
//    }
//}

package com.example.invoiceProject.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.example.invoiceProject.DTO.requests.PrivilegeRequest;
import com.example.invoiceProject.DTO.requests.RoleRequest;
import com.example.invoiceProject.DTO.response.PrivilegeResponse;
import com.example.invoiceProject.DTO.response.RoleResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.PrivilegeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.example.invoiceProject.Repository.RoleRepository;
import com.example.invoiceProject.Model.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse create(RoleRequest request) {
        var role = mapper.map(request, Role.class);

        if (roleRepository.existsByRoleName(request.getRoleName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        try {


        var privileges = privilegeRepository.findAllById(request.getPrivileges());

        role.setPrivileges(new ArrayList<>(privileges) {
        });

        role = roleRepository.save(role);

        }catch (DataIntegrityViolationException e) {
            // Handle duplicate entry or constraint violation
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return mapper.map(role, RoleResponse.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream()
                .map(role -> mapper.map(role, RoleResponse.class))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long role) {
        roleRepository.deleteById(role);
    }


    public RoleResponse update(Long id, RoleRequest request){
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_EXISTED));

        mapper.map(role, request);
        role = roleRepository.save(role);

        return mapper.map(request, RoleResponse.class);
    }
}
