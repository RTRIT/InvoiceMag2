package com.example.invoiceProject.Service;


import com.example.invoiceProject.DTO.requests.AuthenticationRequest;
import com.example.invoiceProject.DTO.requests.IntrospectRequest;
import com.example.invoiceProject.DTO.requests.LogoutRequest;
import com.example.invoiceProject.DTO.requests.RefreshRequest;
import com.example.invoiceProject.DTO.response.AuthenticationResponse;
import com.example.invoiceProject.DTO.response.IntrospectResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.InvalidToken;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.InvalidatedTokenRepository;
import com.example.invoiceProject.Repository.UserRepository;
import com.example.invoiceProject.Service.JwtService.JwtService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class AuthenticateService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        String token = request.getToken();

        jwtService.verifyToken(token);

        return IntrospectResponse.builder()
                .valid(true)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    //Log out

    public void logout(LogoutRequest request)
            throws ParseException, JOSEException {
        var signedJWT = jwtService.verifyToken(request.getToken());

        String jti = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidToken invalidToken = new InvalidToken(jti, expiryTime);

        invalidatedTokenRepository.save(invalidToken);

    }

    public AuthenticationResponse refreshToken(RefreshRequest request)
            throws ParseException, JOSEException {

        var signedJWT = jwtService.verifyToken(request.getToken());

        var jti = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidToken invalidToken = new InvalidToken(jti, expiryDate);

        invalidatedTokenRepository.save(invalidToken);

        var email = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByEmail(email)
                .orElseThrow(()->  new AppException(ErrorCode.USER_IS_NOT_EXISTED));

        var refreshToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(refreshToken)
                .authenticated(true)
                .build();
    }




}
