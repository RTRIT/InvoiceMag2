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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("user")
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
    @Autowired
    private ModelMapper mapper;


    @GetMapping("/list")
    public String getListUser(ModelMap model){
        model.addAttribute("users", userService.getListUser() );
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return "user/index";
    }

    @GetMapping("/new")
    public String registrationForm(ModelMap model){

        UserCreationRequest userCreationRequest = new UserCreationRequest();
        model.addAttribute("user", userCreationRequest);
        return "user/new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") UserCreationRequest userCreationRequest, ModelMap model) {

        try {
            User user = mapper.map(userCreationRequest, User.class);
            UserResponse userResponse = userService.createUser(userCreationRequest);

            model.addAttribute("message", "Registration successful!");
            return "redirect:/user/list";
        } catch (Exception e) {

            model.addAttribute("error", "Failed to register user: " + e.getMessage());
            return "new";
        }
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }


    @PostMapping("/delete/{email}")
    public String delete(@PathVariable String email){
        System.out.println(email);
        userService.delete(email);
        return "redirect:/user/list";
    }


    @GetMapping("/update/{email}")
    public String update(ModelMap model, @PathVariable String email){
        UserResponse user = userService.getUserByEmail(email);
        System.out.println("get update 1 "+user);
        UserUpdateRequest updateRequest = mapper.map(user, UserUpdateRequest.class);
        System.out.println("get update 2 "+ updateRequest);
        model.addAttribute("user", updateRequest);
        return "user/update";
    }


    @PostMapping("/update/{userMail}")
    public String updateUser(@PathVariable String userMail, @ModelAttribute("user") UserUpdateRequest request) {
//        UUID uuid = UUID.fromString(userId);
//        System.out.println(uuid);
        System.out.println(request);
        userService.update(userMail, request);
        return "redirect:/user/list";
    }

    //Get list user




    @GetMapping("/api/user/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

//    @PostMapping("/user/resetPassword")
//    public MailReponse resetPassword(@RequestBody String userEmail) {
//        return emailService.sendMailResetPassword(userEmail);
//    }

}
