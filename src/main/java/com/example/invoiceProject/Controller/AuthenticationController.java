package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.requests.IntrospectRequest;
import com.example.invoiceProject.DTO.requests.LogoutRequest;
import com.example.invoiceProject.DTO.requests.RefreshRequest;
import com.example.invoiceProject.DTO.requests.MailResetRequest;
import com.example.invoiceProject.DTO.response.ApiResponse;

import com.example.invoiceProject.DTO.response.AuthenticationResponse;
import com.example.invoiceProject.DTO.response.IntrospectResponse;

import com.example.invoiceProject.Service.AuthenticateService;
import com.example.invoiceProject.Service.EmailService;
import com.example.invoiceProject.Service.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")

public class AuthenticationController {
    @Autowired
    private AuthenticateService authenticationService;
    private UserService userService;
//    @Autowired
//    private EmailService emailService;


//    @PostMapping("/token")
//    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
//        var result = authenticationService.authenticate(request);
//        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
//    }


//    @PostMapping("/introspect")
//    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
//            throws ParseException, JOSEException {
//        var result = authenticationService.introspect(request);
//
//        return ApiResponse.<IntrospectResponse>builder().result(result).build();
//    }



//    @PostMapping("/refresh")
//    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request)
//            throws ParseException, JOSEException {
//        var result = authenticationService.refreshToken(request);
//        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
//    }

//
//    @GetMapping("/logout")
//    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
//        authenticationService.logout(request);
//        return ApiResponse.<Void>builder().build();
//    }
}