package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.requests.UserUpdateRequest;
import com.example.invoiceProject.DTO.response.ApiResponse;
import com.example.invoiceProject.DTO.requests.UserCreationRequest;
//import com.example.invoiceProject.DTO.response.GenericResponse;
import com.example.invoiceProject.DTO.response.DepartmentResponse;
import com.example.invoiceProject.DTO.response.MailReponse;
import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Model.Department;
import com.example.invoiceProject.Model.MailDetail;
import com.example.invoiceProject.Model.PasswordResetToken;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.PasswordResetTokenRepository;
import com.example.invoiceProject.Service.AuthenticateService;
import com.example.invoiceProject.Service.DepartmentService;
import com.example.invoiceProject.Service.EmailService;
import com.example.invoiceProject.Service.JwtService.JwtService;
import com.example.invoiceProject.Service.RoleService;
import com.example.invoiceProject.Service.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.SystemEventListener;
import java.text.ParseException;
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
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    AuthenticateService authenticateService;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;


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
        model.addAttribute("departments", departmentService.getList());
        return "user/new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") UserCreationRequest userCreationRequest,
                             ModelMap model,
                             @RequestParam("departmentMail") String departmentMail) {
        try {
            System.out.println("This is department: "+departmentMail);
            //Get department by email
            Department department = departmentService.findByEmail(departmentMail);

            System.out.println("Department entity: "+department);


//            User user = mapper.map(userCreationRequest, User.class);
            userCreationRequest.setDepartment(department);
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


    @PostMapping("/delete")
    public String delete(@RequestParam String email){
        System.out.println("email of user want to delete"+email);
        userService.delete(email);
        return "redirect:/user/list";
    }


    @GetMapping("/update")
    public String update(ModelMap model, @RequestParam String email){
        UserResponse user = userService.getUserByEmail(email);
        System.out.println("get update 1 "+user);
        UserUpdateRequest updateRequest = mapper.map(user, UserUpdateRequest.class);
        System.out.println("get update 2 "+ updateRequest);
        model.addAttribute("user", updateRequest);
        return "user/update";
    }


    @PostMapping("/update")
    public String updateUser( @ModelAttribute("user") UserUpdateRequest request) {
//        UUID uuid = UUID.fromString(userId);
//        System.out.println(uuid);
        System.out.println(request.getEmail());
        userService.update(request.getEmail(), request);
        return "redirect:/user/list";
    }


    @GetMapping("/changePassword")
    public String showChangePasswordPage( Model model,
                                          @RequestParam("token") String token) {
        AuthenticateService.TokenStatus result = authenticateService.validatePasswordResetToken(token);


        if(result == AuthenticateService.TokenStatus.VALID) {
            model.addAttribute("message", token);
            model.addAttribute("token" , token);
            return "/user/updatePassword";
        }
        model.addAttribute("token" , token);
        model.addAttribute("error", result.toString());
        return "error";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @RequestParam("token") String token, Model model) {

        if (confirmPassword.equals(newPassword)) {
            User user = userService.getUserByResetToken(token);
            userService.changeUserPassword(user, newPassword);
            model.addAttribute("message", "Password changed!");
        } else {
            model.addAttribute("error", "Password does not match!");
        }
        return "error";
    }

}
