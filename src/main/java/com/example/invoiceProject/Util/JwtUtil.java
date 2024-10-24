//package com.example.invoiceProject.Util;
//
//import com.example.invoiceProject.Exception.AppException;
//import com.example.invoiceProject.Exception.CustomException;
//import com.example.invoiceProject.Exception.ErrorCode;
//import com.example.invoiceProject.Model.Role;
//import com.example.invoiceProject.Model.User;
//import com.example.invoiceProject.Service.JwtService.SecretService;
//import com.example.invoiceProject.Service.UserService;
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import lombok.Value;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//import java.security.Key;
//import java.util.*;
//import java.util.function.Function;
//
////Perform Jwt operations: creation, validation
//@Component
//public class JwtUtil implements Serializable {
//    @Autowired
//    SecretService secretService;
//    @Autowired @Lazy
//    UserService userService;
//
//    private final String SECRET_KEY = "4261656C64756E67";
//
//
//    //Retrieve username form jwt token
////    public String extractUsername(String token){
////        return extractClaimFromToken(token, Claims::getSubject);
////    }
//
//    //Retrieve expiration date from jwt token
////    private Date extractExpiration(String token) {
////        return extractClaimFromToken(token, Claims::getExpiration);
////    }
//
//
//    //T is generic type parameter(allow method to be flexible in return type)
//    //Function<Claims, T> claimsResolver: is a functional Interface that take Claims Obj as input and T as output
//    // This function take token and retrieve a Claims obj( which contains pieces of information )
////    private <T> T extractClaimFromToken(String token, Function<Claims, T> claimsResolver) {
////        final Claims claims = extractAllClaims(token);
////        return claimsResolver.apply(claims);
////    }
//
//    // For retrieving any information from token we will need secret key
////    private Claims extractAllClaims(String token) {
////        try {
////            return Jwts.parserBuilder()
////                    .setSigningKeyResolver(secretService.getSigningKeyResolver())
////                    .build()
////                    .parseClaimsJws(token)
////                    .getBody();
////        } catch (ExpiredJwtException e) {
////            throw new AppException( ErrorCode.TOKEN_EXPIRED);
////        } catch (UnsupportedJwtException e) {
////            throw new AppException( ErrorCode.TOKEN_UNSUPPORTED);
////        } catch (MalformedJwtException e) {
////            throw new AppException( ErrorCode.TOKEN_MALFORMED);
////        } catch (SignatureException e) {
////            throw new AppException( ErrorCode.TOKEN_INVALID_SIGNATURE);
////        } catch (IllegalArgumentException e) {
////            throw new AppException( ErrorCode.TOKEN_ILLEGAL_ARGUMENT);
////        }
////    }
//
//
//    //Check if the token has expired
////    public Boolean isTokenExpired(String token) {
////        return extractExpiration(token).before(new Date());
////    }
//
//
//    //Generate token for user
////    public String generateToken(String username) {
////        Map<String, Object> claims = new HashMap<>();
////
////        //Get role by username(email)
////        List<Role> role = userService.getUserByUsername(username)
////                .orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED))
////                        .getRoles();
////
////        claims.put("roleName", role);
////        return createToken(claims, username);
////    }
//
//
//    //Define claims of the token
//    //Sign the jwt using Hs512 and secret key
//    //According to JWS Compact Serialization
////    private String createToken(Map<String, Object> claims, String subject) {
////
////        return Jwts.builder()
////                .setClaims(claims)
////                .setSubject(subject)
////                .setIssuedAt(new Date(System.currentTimeMillis()))
////                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
////                .signWith(
////                        SignatureAlgorithm.HS256, //Algorithm dùng để mã hoá key
////                        secretService.getHS256SecretBytes()// Hàm trả về key dứoi dạng bytes
////                )
////                .compact();
////    }
//
//    //Validate token to make sure it hasn't been tampered or expired
////    public Boolean validateToken(String token, String username) {
////        String extractedUsername = extractUsername(token);
////        System.out.println(extractedUsername);
////        System.out.println(username);
////        return (extractedUsername.trim().equals(username.trim() ));
////    }
//
//}
