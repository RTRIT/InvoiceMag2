package com.example.invoiceProject.Controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.invoiceProject.DTO.requests.UpdateMyInfoRequest;
import com.example.invoiceProject.DTO.requests.UpdatePasswordRequest;
import com.example.invoiceProject.DTO.requests.UserUpdateRequest;
import com.example.invoiceProject.DTO.response.*;
import com.example.invoiceProject.DTO.requests.UserCreationRequest;
//import com.example.invoiceProject.DTO.response.GenericResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.*;
import com.example.invoiceProject.Repository.PasswordResetTokenRepository;
import com.example.invoiceProject.Service.AuthenticateService;
import com.example.invoiceProject.Service.DepartmentService;
import com.example.invoiceProject.Service.EmailService;
import com.example.invoiceProject.Service.JwtService.JwtService;
import com.example.invoiceProject.Service.RoleService;
import com.example.invoiceProject.Service.UserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.desktop.SystemEventListener;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
//        List<User> userList = userService.getListUser();
        List<User> userList = userService.getListUserByStatus(1);
        List<UserResponse> userResponseList = userList.stream().map(user -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setEmail(user.getEmail());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setRoles(user.getRoles());
//            userResponse.setDepartment(user.getDepartment().getNameDepartment());

            // Set department name if department is not null
            if (user.getDepartment() != null) {
                userResponse.setDepartment(user.getDepartment());
            } else {
                userResponse.setDepartment(null);
            }

            return userResponse;
        }).toList();
        model.addAttribute("users", userResponseList);


        return "user/index";
    }

    @GetMapping("/new")
    public String registrationForm(ModelMap model){

        UserCreationRequest userCreationRequest = new UserCreationRequest();
        model.addAttribute("user", userCreationRequest);
        model.addAttribute("departments", departmentService.getList());
        model.addAttribute("roles", roleService.getAll());
        return "user/new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") UserCreationRequest userCreationRequest,
                             ModelMap model,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("departmentMail") String departmentMail,
                             @RequestParam("roleId") Long roleId) {
        System.out.println("This is role id:"+roleId);
        try {
            System.out.println("This is department: "+departmentMail);
            //Get department by email
            Department department = departmentService.findByEmail(departmentMail);
            RoleResponse roleRes = roleService.getRoleById(roleId);
            Role role = mapper.map(roleRes, Role.class);
            System.out.println("Department entity: "+department);
            System.out.println("Role of user: "+ role);

//            User user = mapper.map(userCreationRequest, User.class);

            userCreationRequest.setDepartment(department);
            userCreationRequest.setRole(role);
            UserResponse userResponse = userService.createUser(userCreationRequest);

            redirectAttributes.addFlashAttribute("message", "Add new user successful!");
            return "redirect:/user/new";
        } catch (AppException e) {

            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/new";
        }
    }

//    @GetMapping("/my-info")
//    ApiResponse<UserResponse> getMyInfo() {
//        return ApiResponse.<UserResponse>builder()
//                .result(userService.getMyInfo())
//                .build();
//    }



    @PostMapping("/delete")
    public String delete(@RequestParam String email){
        userService.delete(email);
        return "redirect:/user/list";
    }


    @GetMapping("/update")
    public String update(ModelMap model, @RequestParam String email) {
        UserResponse user = userService.getUserByEmail(email);
        List<Long> userRoles = user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        List<RoleResponse> roleList = roleService.getAll();


        String department = (user.getDepartment() != null) ? user.getDepartment().getNameDepartment() : "No Department";

        model.addAttribute("user", user);
        model.addAttribute("departmentName", department);
        model.addAttribute("departmentList", departmentService.getList());
        model.addAttribute("userRoles", userRoles);
        model.addAttribute("roleList", roleList);

        return "user/update";
    }



    @PostMapping("/update")
    public String updateUser( @ModelAttribute("user") UserUpdateRequest request) {
        System.out.println(request);
//        UUID uuid = UUID.fromString(userId);
//        System.out.println(uuid);
//        System.out.println(request.getEmail());
        userService.update(request.getEmail(), request);
        return "redirect:/user/list";
    }

    //Người dùng muốn thay đổi mật khẩu
    @GetMapping("/changePassword")
    public String showChangePasswordPage( Model model,
                                          HttpServletRequest request
//                                          @RequestParam("token") String token
    ) throws ParseException, JOSEException {

        String token = authenticateService.getTokenFromCookie(request);

        AuthenticateService.TokenStatus result = authenticateService.validateUpdatePasswordToken(token);

        if(result == AuthenticateService.TokenStatus.VALID) {
            model.addAttribute("message", token);
            model.addAttribute("token" , token);
            return "/user/updatePassword";
        }
        model.addAttribute("token" , token);
        model.addAttribute("error", result.toString());
        return "error";
    }


    @GetMapping("/myInfo/updatePassword")
    public String showUpdatePasswordForm(Model model) {
        model.addAttribute("resetPasswordRequest", new UpdatePasswordRequest());
        return "user/myInfoUpdatePassword";
    }



    @PostMapping("/myInfo/updatePassword")
    public String updatePassword(
            HttpServletRequest request,
            @Valid @ModelAttribute("resetPasswordRequest") UpdatePasswordRequest resetPasswordRequest,
            BindingResult result,
            RedirectAttributes redirectAttributes) throws ParseException, JOSEException {

        // Kiểm tra lỗi validation
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.resetPasswordRequest", result);
            redirectAttributes.addFlashAttribute("resetPasswordRequest", resetPasswordRequest);
            return "redirect:/user/myInfo/updatePassword";
        }

        // Lấy thông tin người dùng hiện tại
        UserResponse userResponse = userService.getUserByCookie(request);
        User user = userService.getUserById(userResponse.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));

        // Xác minh mật khẩu hiện tại
        if (!authenticateService.authenticate(user.getEmail(), resetPasswordRequest.getCurrentPassword())) {
            redirectAttributes.addFlashAttribute("error", "Current password does not match!");
        }

        else if (!userService.validatePassword(resetPasswordRequest.getNewPassword(), resetPasswordRequest.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("error", "New password must be at least 8 characters !");
        }



        // Kiểm tra khớp mật khẩu mới và xác nhận
        else if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("error", "New password and confirmation password do not match!");
        }



        else if (resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getCurrentPassword())) {
            redirectAttributes.addFlashAttribute("error", "New password must be different from the current password!");
        }

        // Đổi mật khẩu nếu tất cả hợp lệ
        else {
            userService.changeUserPassword(user, resetPasswordRequest.getNewPassword());
            redirectAttributes.addFlashAttribute("message", "Password changed successfully!");
        }

        return "redirect:/user/myInfo/updatePassword";
    }



    @PostMapping("/updatePassword")
    public String updatePassword(HttpServletRequest request,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
//                                 @RequestParam("token") String token,
                                 Model model) throws ParseException, JOSEException {

        String token = authenticateService.getTokenFromCookie(request);





        if (confirmPassword.equals(newPassword) || userService.validatePassword(newPassword, confirmPassword)) {

//            User user = userService.getUserByResetToken(token);
            UserResponse userResponse = userService.getUserByCookie(request);
            User user = userService.getUserById(userResponse.getId())
                            .orElseThrow(()->new AppException(ErrorCode.USER_IS_NOT_EXISTED));

            userService.changeUserPassword(user, newPassword);
            model.addAttribute("message", "Password changed!");
            return "redirect:/user/my-info";
        } else {
            model.addAttribute("error", "Password does not match!");
        }
        return "error";
    }
    @GetMapping("/my-info")
    public String myInfo(HttpServletRequest request, ModelMap model) throws ParseException, JOSEException {
        UserResponse userResponse = userService.getUserByCookie(request);
        Department department = userResponse.getDepartment();
        List<Role> roles = userResponse.getRoles();
        model.addAttribute("user", userResponse);
        model.addAttribute("department", department);
        model.addAttribute("roles", roles);
        return "user/my-info";
    }

    @PostMapping("/my-info/update")
    public String updateMyInfo(HttpServletRequest request,
                               ModelMap model,
                               @RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName

    ) throws ParseException, JOSEException {

        UpdateMyInfoRequest updateMyInfoRequest = new UpdateMyInfoRequest(firstName, lastName);

        if(userService.updateMyInfo(updateMyInfoRequest, request)!=null){
            return "redirect:/user/my-info";
        }
        model.addAttribute("error", "Error in update!");
        return "redirect:/user/my-info";
    }

}
