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
import com.example.invoiceProject.Model.PasswordResetToken;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.InvalidatedTokenRepository;
import com.example.invoiceProject.Repository.PasswordResetTokenRepository;
import com.example.invoiceProject.Repository.UserRepository;
import com.example.invoiceProject.Service.JwtService.JwtService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class AuthenticateService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticateService.class);
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        String token = request.getToken();

        jwtService.verifyToken(token, false);

        return IntrospectResponse.builder()
                .valid(true)
                .build();
    }

//    public AuthenticationResponse authenticate(String username, String password){
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//
//        User user = userRepository.findByEmail(username);
//
//        boolean authenticated = passwordEncoder.matches(password, user.getPassword());
//
//        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);
//
//        String token = jwtService.generateToken(user);
//
//        return AuthenticationResponse.builder().token(token).authenticated(true).build();
//    }


    public boolean authenticate(String username, String password) throws ParseException, JOSEException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (userRepository.existsByEmail(username)) {
            User user = userRepository.getUserByEmail(username);

            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    //Log out

    public void logout(LogoutRequest request)
            throws ParseException, JOSEException {
        try{
            //Việc log out rồi không cần để ý đến việc jwt bị hết hạn hay không
            // Set true vì người dùng có thể lấy token đem đi refresh vì token này
            // còn khả năng refresh
            var signedJWT = jwtService.verifyToken(request.getToken(),true);
            String jti = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            InvalidToken invalidToken = new InvalidToken(jti, expiryTime);

            invalidatedTokenRepository.save(invalidToken);
        }catch (AppException exception){
            log.info("Token already expired!");
        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest request)
            throws ParseException, JOSEException {

        //Verify the current token is valid
        var signedJWT = jwtService.verifyToken(request.getToken(), true);

        // save the current token into invalidToken Repository
        var jti = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidToken invalidToken = new InvalidToken(jti, expiryDate);
        invalidatedTokenRepository.save(invalidToken);

        //Create the refresh token for user
        var email = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByEmail(email)
                .orElseThrow(()->  new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        var refreshToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(refreshToken)
                .authenticated(true)
                .build();
    }
    public enum TokenStatus {
        VALID, INVALID, EXPIRED
    }


    public TokenStatus validatePasswordResetToken(String token) {
        PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        if (!isTokenFound(passToken)) {
            return TokenStatus.INVALID;
        }

        if (isTokenExpired(passToken)) {
            return TokenStatus.EXPIRED;
        }

        return TokenStatus.VALID;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        return passToken.getExpiryDate().before(new Date());
    }




}
