package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/login")
    public ResponseEntity loginController(@RequestBody User loginForm){

        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Please fill in all the field!!!");
        }
        User isUser = userService.authenticate(username, password);
        if(isUser == null){
            return ResponseEntity.ok().body("User " + username+" or "+ " username or password is not correct.");
        }
        return ResponseEntity.ok("Welcome "+username);

    }

    @PostMapping("/register")
    public ResponseEntity registerController(@RequestBody User registerForm){
        String username = registerForm.getUsername();
        String password = registerForm.getPassword();

        //Check whether all field are filled in
        if(username == null || password == null){
            return ResponseEntity.ok("Please fill all the field!");
        }

        //Check if username is already existed
        User user = userService.getUserByUsername(username);
        if(user != null){
            return ResponseEntity.ok("Username is already existed!!");
        }

        //Register user
        userService.register(username, password);
        return ResponseEntity.ok("test");
    }

    @PutMapping("/user/{id}")
    public void updateController(@RequestBody User updateUser, @PathVariable Long id){
        String username = updateUser.getUsername();
        String password = updateUser.getPassword();
        String fullName = updateUser.getFullName();
        userService.updateUser(username,password,fullName,id);
    }



}
