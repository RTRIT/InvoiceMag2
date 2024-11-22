package com.example.invoiceProject.Service;


import com.example.invoiceProject.DTO.requests.UserCreationRequest;
import com.example.invoiceProject.DTO.requests.UserUpdateRequest;
import com.example.invoiceProject.DTO.response.ApiResponse;
import com.example.invoiceProject.DTO.response.MailReponse;
import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.*;
import com.example.invoiceProject.Repository.*;
import com.example.invoiceProject.Service.JwtService.JwtService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Autowired
    private JavaMailSender mailSender;

    private UserService userService;

    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;


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

//    @PostAuthorize("returnObject.email == authentication.name")
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
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));

        return mapper.map(user, UserResponse.class);
    }

//    @PostAuthorize("returnObject.email == authentication.name  || hasRole('ADMIN')")
    public UserResponse update(String  userMail, UserUpdateRequest request) {
        User user = userRepository.findByEmail(userMail).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        // chua bat validation
//   s
        mapper.map(request, user); // Map non-null fields from request to user

        User updatedUser = userRepository.save(user);
        return mapper.map(updatedUser, UserResponse.class);

    }



    public boolean userExist(String email){
        return userRepository.existUser(email);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }
//
//    public MailReponse requstResetPasswordToken(String userEmail) {
//        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
//
//        String token = UUID.randomUUID().toString();
//        userService.
//    }
}
