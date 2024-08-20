package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.RoleService;
import com.example.invoiceProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    //Login
    @PostMapping("/login")
    public ResponseEntity loginController(@RequestBody User loginForm){

        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        //Check field filled up all (Xử lý ở front end)
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Please fill in all the field!!!");
        }

        User isUser = userService.login(email, password);
        if(isUser == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Welcome "+email);

    }

    //Register
    @PostMapping("/register")
    public ResponseEntity registerController(@RequestBody User registerForm){
        String email = registerForm.getEmail();
        String password = registerForm.getPassword();


        //Check field filled up all (Xử lý ở front end)
        if(email == null || password == null){
            return ResponseEntity.ok("Please fill all the field!");
        }
        //Pattern the email (Xử lý ở Front end)

        //Register user
        userService.register(email, password);
        return ResponseEntity.ok("Register Successfully!");
    }

    //Update user by id
    @PutMapping("/user/{id}/edit")
    public void updateController(@RequestBody User updateUser, @PathVariable int id){
        String email = updateUser.getEmail();
        String password = updateUser.getPassword();
        userService.updateUser(email,password, id);
    }

    //Delete user by id
    @DeleteMapping("/user/{id}/delete")
    public void deleteController(@PathVariable Long id){
        userService.deleteUser(id);
    }

    //Get list user
    @GetMapping("/users")
    public List<User> getListUser(){
        return userService.getListUser();
    }

    //Get user by id
    @GetMapping("/users/{id}")
    public ResponseEntity getUserById(@PathVariable(value = "id") Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }

}
