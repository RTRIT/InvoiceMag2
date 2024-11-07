package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.requests.UserUpdateRequest;
import com.example.invoiceProject.DTO.response.ApiResponse;
import com.example.invoiceProject.DTO.requests.UserCreationRequest;
import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.JwtService.JwtService;
import com.example.invoiceProject.Service.RoleService;
import com.example.invoiceProject.Service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/api/register")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        UserResponse userResponse = userService.createUser(request);
        return ApiResponse.<UserResponse>builder()
                .result(userResponse).build();
    }

    @GetMapping("/user/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }




    @DeleteMapping("/api/user/{username}/delete")
    public void delete(@PathVariable String username){
        userService.delete(username);
    }


//    @PutMapping("/api/user/{userMail}/update")
//    ApiResponse<UserResponse> updateUser(@PathVariable String userMail, @RequestBody UserUpdateRequest request) {
////        UUID uuid = UUID.fromString(userId);
////        System.out.println(uuid);
//        System.out.println(request);
//        return ApiResponse.<UserResponse>builder()
//                .result(userService.update(userMail, request))
//                .build();
//    }

    //Get list user

    @GetMapping("/api/users")
    public List<User> getListUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("UserName :  " + authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return userService.getListUser();
    }

//



}
