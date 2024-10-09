package com.example.invoiceProject.Controller.JwtController;

import com.example.invoiceProject.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jwt")
public class TestJwt {
    @Autowired
    JwtUtil jwtUtil;
    @PostMapping("/createJwt")
    public ResponseEntity<String> getJwt(@RequestParam String username){
        return ResponseEntity.ok(jwtUtil.generateToken(username));
    }
    @GetMapping("/validateJwt")
    public ResponseEntity<String> validateToken(@RequestParam String token, @RequestParam String email){
//        return ResponseEntity.ok("Get into the function");
        if(jwtUtil.validateToken(token, email)){
            return ResponseEntity.ok("token is validated");
        }
        return ResponseEntity.ok("token is not validated");



    }
    @GetMapping("/expiredJwt")
    public ResponseEntity<String> expireToken(@RequestParam String token){
        if(jwtUtil.isTokenExpired(token)){
            return ResponseEntity.ok("The token is not expired yet!!");
        }
        return ResponseEntity.ok("The token is Expired!!");
    }

}
