package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.requests.UserUpdateRequest;
import com.example.invoiceProject.DTO.response.ApiResponse;
import com.example.invoiceProject.DTO.requests.UserCreationRequest;
import com.example.invoiceProject.DTO.response.MailReponse;
import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Model.MailDetail;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.EmailService;
import com.example.invoiceProject.Service.JwtService.JwtService;
import com.example.invoiceProject.Service.RoleService;
import com.example.invoiceProject.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    private EmailService emailService;





    @PostMapping("/api/register")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){

        UserResponse userResponse = userService.createUser(request);
        return ApiResponse.<UserResponse>builder().result(userResponse).build();

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


    @PutMapping("/api/user/{userMail}/update")
    ApiResponse<UserResponse> updateUser(@PathVariable String userMail, @RequestBody UserUpdateRequest request) {
//        UUID uuid = UUID.fromString(userId);
//        System.out.println(uuid);
        System.out.println(request);
        return ApiResponse.<UserResponse>builder()
                .result(userService.update(userMail, request))
                .build();
    }

    //Get list user

    @GetMapping("/api/users")
    public List<User> getListUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return userService.getListUser();
    }

    //Get user by id
    @GetMapping("/api/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "user_id") UUID userId) {
        Optional<User> OptionalUser = userService.getUserById(userId);
        return OptionalUser
                .map(user -> ResponseEntity.ok(user)) // Nếu có giá trị, trả về mã 200 và đối tượng
                .orElseGet(() -> ResponseEntity.notFound().build());
    }




//    @PostMapping("/user/resetPassword")
//    public MailReponse resetPassword(@RequestBody String userEmail) {
//        return emailService.sendMailResetPassword(userEmail);
//    }

}
