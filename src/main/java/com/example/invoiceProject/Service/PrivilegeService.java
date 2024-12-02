package com.example.invoiceProject.Service;


import com.example.invoiceProject.DTO.requests.PrivilegeRequest;
import com.example.invoiceProject.DTO.response.PrivilegeResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
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
        System.out.println(privilege);

        privilege = privilegeRepository.save(privilege);
        return mapper.map(privilege, PrivilegeResponse.class);
    }

    public List<PrivilegeResponse> getList(){
        var privileges = privilegeRepository.findAll();
        return privileges.stream()
                .map(permission -> mapper.map(permission, PrivilegeResponse.class))
                .collect(Collectors.toList());
    }

    public PrivilegeResponse getPrivilege(Long id){
        Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRIVILEGE_IS_NOT_EXISTED));
        return mapper.map(privilege, PrivilegeResponse.class);
    }

    public PrivilegeResponse update(Long id, PrivilegeRequest request){
        Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRIVILEGE_IS_NOT_EXISTED));

        privilege.setName(request.getName());
        privilege.setDescription(request.getDescription());

        Privilege privilegeSave = privilegeRepository.save(privilege);

        return mapper.map(privilegeSave, PrivilegeResponse.class);

    }

    public void delete(Long id){
        privilegeRepository.deleteById(id);
    }
}
