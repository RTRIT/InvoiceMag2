package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.LoginForm;
import com.example.invoiceProject.Model.RegisterForm;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity loginController(@RequestBody LoginForm loginForm){

        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Please fill in all the field!!!");
        }
        User user = userService.authenticate(username, password);
        if(user == null){
            return ResponseEntity.ok().body("User " + username+" or "+ " username or password is not correct.");
        }
        return ResponseEntity.ok("Welcome "+username);

    }

    @PostMapping("/register")
    public ResponseEntity registerController(@RequestBody RegisterForm registerForm){
        String username = registerForm.getUsername();
        String password = registerForm.getPassword();
        String rePassword = registerForm.getRePassword();

        //Check whether all field are filled in
        if(username == null || password == null || rePassword == null){
            return ResponseEntity.badRequest().body("Please fill all the field!");
        }

        //Check if username is already existed
        User user = userService.getUserByUsername(username);
        if(user == null){
            return ResponseEntity.ok("Username is already existed!!");
        }

        //Check if password == repassword
        if(!password.equals(rePassword)){
            return ResponseEntity.ok("Password is not match!!!");
        }

        //Register user
        int result = userService.register(username, password);

        return ResponseEntity.ok(result);
    }
}
