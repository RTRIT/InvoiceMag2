package com.example.invoiceProject.Service;


import com.example.invoiceProject.DTO.requests.PrivilegeRequest;
import com.example.invoiceProject.DTO.response.PrivilegeResponse;
import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Repository.PrivilegeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrivilegeService {
    @Autowired
    PrivilegeRepository privilegeRepository;
    @Autowired
    private ModelMapper mapper;

    public PrivilegeResponse create(PrivilegeRequest request) {
        Privilege privilege = mapper.map(request, Privilege.class);
        privilege = privilegeRepository.save(privilege);
        return mapper.map(privilege, PrivilegeResponse.class);
    }

    public List<PrivilegeResponse> getList(){
        var privileges = privilegeRepository.findAll();
        return privileges.stream()
                .map(permission -> mapper.map(permission, PrivilegeResponse.class))
                .collect(Collectors.toList());
    }

//    public void addPrivilege(Privilege newPrivilege){
//        privilegeRepository.save(newPrivilege);
//
//    }

    public void delete(Long id){
        privilegeRepository.deleteById(id);
    }
}
