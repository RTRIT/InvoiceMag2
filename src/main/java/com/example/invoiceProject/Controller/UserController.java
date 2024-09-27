package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.PaymentTime;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.RoleService;
import com.example.invoiceProject.Service.UserService;
import com.example.invoiceProject.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity loginController(@RequestBody User loginForm){
        User isUser = userService.login(loginForm);
        if(isUser==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        //Generate token with the username(email)
        String token = jwtUtil.generateToken(loginForm.getEmail());

        //Build a response body with token (The response body should be structured)
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Welcome " + isUser.getEmail());
        responseBody.put("token", token);

        return ResponseEntity.ok(responseBody);

    }

    @PostMapping("/register")
    public ResponseEntity registerController(@RequestBody User registerForm){
        //Validate the input and check whether the user exist
        if(userService.userExist(registerForm.getEmail())){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "User already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

        }

        //Attempt register user
        User newUser = userService.register(registerForm);

        //Check if user register successfully
        if(newUser == null){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Registration failed due to server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

        //Build successful response
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("message", "Registration successful");
        successResponse.put("user", Map.of(
                "email", newUser.getEmail()
//                "name", newUser.getName()  // Include non-sensitive user details
        ));

        return ResponseEntity.ok(successResponse);
    }

    @DeleteMapping("/user/{username}/delete")
    public void delete(@PathVariable String username){
        userService.delete(username);
    }

    @PutMapping("/user/{id}/edit")
    public void updateController(@RequestBody User updateForm, @PathVariable int id){
        userService.update(updateForm);
    }

    //Get list user

    @GetMapping("/users")
    public List<User> getListUser(){
        return userService.getListUser();
    }

    //Get user by id
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "user_id") Long userId) {
        Optional<User> OptionalUser = userService.getUserById(userId);
        return OptionalUser
                .map(user -> ResponseEntity.ok(user)) // Nếu có giá trị, trả về mã 200 và đối tượng
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
