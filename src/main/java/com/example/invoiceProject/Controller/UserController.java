package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.response.ApiResponse;
import com.example.invoiceProject.DTO.requests.AuthenticationRequest;
import com.example.invoiceProject.DTO.requests.UserCreationRequest;
import com.example.invoiceProject.DTO.response.AuthenticationResponse;
import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.JwtService.JwtService;
import com.example.invoiceProject.Service.RoleService;
import com.example.invoiceProject.Service.UserService;
import com.example.invoiceProject.Util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
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
        return ApiResponse.<UserResponse>builder().result(userResponse).build();

    }

    @DeleteMapping("/api/user/{username}/delete")
    public void delete(@PathVariable String username){
        userService.delete(username);
    }

    @PutMapping("/api/user/{id}/edit")
    public void updateController(@RequestBody User updateForm, @PathVariable int id){
        userService.update(updateForm);
    }

    //Get list user

    @GetMapping("/api/users")
    public List<User> getListUser(){
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


}
