package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.RoleService;
import com.example.invoiceProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;



    @PostMapping("/login")
    public ResponseEntity loginController(@RequestBody User loginForm){
        User isUser = userService.login(loginForm);
        if(isUser==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Welcome "+loginForm.getEmail());

    }

    @PostMapping("/register")
    public ResponseEntity registerController(@RequestBody User registerForm){
        userService.register(registerForm);
        return ResponseEntity.ok("Register Successfully!");
    }

    @DeleteMapping("/user/{id}/delete")
    public void deleteController(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PutMapping("/user/{id}/edit")
    public void updateController(@RequestBody User updateForm, @PathVariable int id){
        userService.updateUser(updateForm);
    }

    //Get list user

    @GetMapping("/users")
    public List<User> getListUser(){
        return userService.getListUser();
    }

    //Get user by id
    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable(value = "id") Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }


}
