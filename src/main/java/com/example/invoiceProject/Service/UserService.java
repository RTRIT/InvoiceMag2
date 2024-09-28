package com.example.invoiceProject.Service;


import com.example.invoiceProject.DTO.requests.UserAuthentication;
import com.example.invoiceProject.DTO.requests.UserCreationRequest;
import com.example.invoiceProject.DTO.response.AuthenticationResponse;
import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.PrivilegeRepository;
import com.example.invoiceProject.Repository.RoleRepository;
import com.example.invoiceProject.Repository.UserRepository;
import com.example.invoiceProject.Util.JwtUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
    private JwtUtil jwtUtil;



    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    public List<User> getListUser(){
        return userRepository.findAll();
    }

    public AuthenticationResponse authenticateUser(UserAuthentication request){

        User user = mapper.map(request, User.class);

        //Check if user existed
        user = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
       //Check if password is the same
        Optional.of(user)
                .filter(u -> u.getPassword().equals(request.getPassword()))
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        //Generate token
        //Validated token is not changed and expired
        String token = jwtUtil.generateToken(user.getEmail());
        AuthenticationResponse authenticationResponse = mapper.map(user, AuthenticationResponse.class);
        authenticationResponse.setAuthenticated(true);
        authenticationResponse.setToken(token);
        return authenticationResponse;

    }

    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        User user = mapper.map(request, User.class);
        try {
            // Get role
            Role role = roleRepository.findByRoleName("USER");
            user.setRole(role);

            // Save the user
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // Handle duplicate entry or constraint violation
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        //Mapping userEntity to userDto
        return mapper.map(user, UserResponse.class);


    }

    @Transactional
    public void delete(String username){
        Optional<User> user = userRepository.findByEmail(username);
        userRepository.delete(user.get());
    }

    public Optional<User> getUserByUsername(String email) {
        return Optional.ofNullable(userRepository.getUserByEmail(email));
    }

    public void update(User updateForm) {
        User user = userRepository.findById(updateForm.getId()).get(); // Dung get nay khong on

        user.setEmail(updateForm.getEmail());
        user.setPassword(updateForm.getPassword());
        user.setRole(updateForm.getRole());

        userRepository.save(user);
    }
    public boolean userExist(String email){
        return userRepository.existUser(email);
    }

}
