package com.example.invoiceProject.Service;


import com.example.invoiceProject.DTO.requests.UserCreationRequest;
import com.example.invoiceProject.DTO.requests.UserUpdateRequest;
import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.PrivilegeRepository;
import com.example.invoiceProject.Repository.RoleRepository;
import com.example.invoiceProject.Repository.UserRepository;
import com.example.invoiceProject.Service.JwtService.JwtService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        User user = mapper.map(request, User.class);
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        try {
            // set role
            Role role = roleRepository.findByRoleName("USER");
            List<Role> roles = new ArrayList<>();

            roles.add(role);
            user.setRoles(roles);

            //Hash password
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            // Save the user
            userRepository.save(user);


        } catch (DataIntegrityViolationException e) {
            // Handle duplicate entry or constraint violation
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return mapper.map(user, UserResponse.class);


    }


    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));

        return mapper.map(user, UserResponse.class);
    }



    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getListUser(){
        return userRepository.findAll();
    }



    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String username){
        Optional<User> user = userRepository.findByEmail(username);
        userRepository.delete(user.get());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Optional<User> getUserByUsername(String email) {
        return Optional.ofNullable(userRepository.getUserByEmail(email));
    }

    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse update(String  userId, UserUpdateRequest request) {
        User user = userRepository.findByEmail(userId).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        // chua bat validation
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        var roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new ArrayList<>(roles));

        userRepository.save(user);

        return mapper.map(user, UserResponse.class);
    }
    public boolean userExist(String email){
        return userRepository.existUser(email);
    }

}
