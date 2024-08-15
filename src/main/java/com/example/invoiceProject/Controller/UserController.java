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


    @GetMapping("/users/{id}")
    public ResponseEntity getUserById(@PathVariable(value = "id") Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body("its passing");
    }

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

        //Create Department Entity
//        Department department = new Department();
//        department.setNameDepartment("Seller");

        //Create Role Entity
//        Role role = new Role();
//        role.setRoleName("User");



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
        Role role = roleService.getRoleByName("Test"); //Get Role from db
        userService.register(email, password, role.getId());


//        return new ResponseEntity<>("Hello World!", HttpStatus.OK); // create new ResponseEntity Object using constructor
        return ResponseEntity.ok("Register Successfully!");
    }

    //Update
    @PutMapping("/user/{id}/edit")
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
