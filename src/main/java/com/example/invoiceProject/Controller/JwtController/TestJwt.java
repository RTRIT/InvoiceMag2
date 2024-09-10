package com.example.invoiceProject.Controller.JwtController;

import com.example.invoiceProject.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/jwt")
public class TestJwt {
//    @Autowired
//    JwtUtil jwtUtil;
//    @GetMapping("/createJwt")
//    public ResponseEntity<String> getJwt(@RequestParam String username){
//        return ResponseEntity.ok(jwtUtil.generateToken("tri"));
//    }
//    @GetMapping("/validateJwt")
//    public ResponseEntity<Boolean> validateToken(@RequestParam String token){
////        if(jwtUtil.validateToken(token, "tri"))
////        return ResponseEntity.ok();
//    }

}
