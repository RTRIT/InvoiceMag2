package com.example.invoiceProject.Util;

import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.JwtService.SecretService;
import com.example.invoiceProject.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {
    @Autowired
    SecretService secretService;
    @Autowired @Lazy
    UserService userService;

    private String SECRET_KEY = "secret";

    //Retrieve username form jwt token
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);

    }

    //Retrieve expiration date from jwt token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //T is generic type parameter(allow method to be flexible in return type)
    //Function<Claims, T> claimsResolver: is a functional Interface that take Claims Obj as input and T as output
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // This function take token and retrieve a Claims obj( which contains pieces of information )
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKeyResolver(secretService.getSigningKeyResolver())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Tạo token với tham số truyền vào là username
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        //Get role by username(email)
        String role = userService.getUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED))
                        .getRole().getRoleName();

        claims.put("roleName", role);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(
                        SignatureAlgorithm.HS256,
                        secretService.getHS256SecretBytes()
                )
                .compact();
    }

    //Validate token to make sure it hasn't been tampered or expired
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

}
