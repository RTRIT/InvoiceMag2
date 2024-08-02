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

        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Please fill in all the field!!!");
        }
        User isUser = userService.authenticate(email, password);
        if(isUser == null){
            return ResponseEntity.ok().body("User " + email+" or "+ " email or password is not correct.");
        }
        return ResponseEntity.ok("Welcome "+email);

    }

    @PostMapping("/register")
    public ResponseEntity registerController(@RequestBody User registerForm){
        String email = registerForm.getEmail();
        String password = registerForm.getPassword();

        //Check whether all field are filled in
        if(email == null || password == null){
            return ResponseEntity.ok("Please fill all the field!");
        }

        //Check if email is already existed
        User user = userService.getUserByUsername(email);
        if(user != null){
            return ResponseEntity.ok("Username is already existed!!");
        }

        //Register user
        userService.register(email, password);
        return ResponseEntity.ok("test");
    }

    @PutMapping("/user/{id}")
    public void updateController(@RequestBody User updateUser, @PathVariable Long id){
        String email = updateUser.getEmail();
        String password = updateUser.getPassword();
        userService.updateUser(email,password);
    }



}
