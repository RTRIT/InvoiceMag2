package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.RoleService;
import com.example.invoiceProject.Service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.lang.Integer;

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

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Please fill in all the field!!!");
        }
        User isUser = userService.authenticate(email, password);
        if(isUser == null){
            return ResponseEntity.ok().body("User " + email+" or "+ "password is not correct.");
        }
        return ResponseEntity.ok("Welcome "+email);

    }

    //Register
    @PostMapping("/register")
    public ResponseEntity registerController(@RequestBody User registerForm){

        String email = registerForm.getEmail();
        String password = registerForm.getPassword();



        //Check whether all field are filled in
        if(email == null || password == null){
            return ResponseEntity.ok("Please fill all the field!");
        }

        //Pattern the email


        //Check if email is already existed
        User user = userService.getUserByUsername(email);
        if(user != null){
            return ResponseEntity.ok("This email is already existed!!");
        }

        //Register user
        Role role = roleService.getRoleByName("User");
        userService.register(email, password, role.getId());


//        return new ResponseEntity<>("Hello World!", HttpStatus.OK); // create new ResponseEntity Object using constructor
        return ResponseEntity.ok("Register Successfully!");
    }

    //Update
    @PutMapping("/user/{id}")
    public void updateController(@RequestBody User updateUser, @PathVariable int id){
        String email = updateUser.getEmail();
        String password = updateUser.getPassword();
        userService.updateUser(email,password, id);
    }

    //Delete
    @DeleteMapping("/user/{id}/delete")
    public void deleteController(@PathVariable Long id){
        userService.deleteUser(id);
    }

    //Get list user
    @GetMapping("/users")
    public List<User> getListUser(){
        return userService.getListUser();
    }

}
