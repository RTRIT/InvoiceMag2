package com.example.invoiceProject.Service.JwtService;


import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.InvalidatedTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;


@Component
public class JwtService  {

    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.secret}")
    private String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid_duration}")
    private long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable_duration}")
    private long REFRESHABLE_DURATION;

    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("templates/user/admin")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
//            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getRoleName());
                if (!CollectionUtils.isEmpty(role.getPrivileges()))
                    role.getPrivileges().forEach(permission -> stringJoiner.add(permission.getName()));
            });
        return stringJoiner.toString();
    }

//    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {

    //Check whether the right sign key and the token is not expired
    public SignedJWT verifyToken(String token, boolean isRefresh ) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                    ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                    :signedJWT.getJWTClaimsSet().getExpirationTime();

        String jti = signedJWT.getJWTClaimsSet().getJWTID();

        //verify the specified signature of a JWS object, return boolean
        var verified = signedJWT.verify(verifier);

        //verified the specified signature and whether the specified expiredTime is still in range()
        // if the expiryTime is after the current date, it means that the token is still valid
        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        //Check whether the provided token is in invalid repo
        if (invalidatedTokenRepository.existsById(jti)){;
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    public String getSubjectFromToken(String token) throws ParseException, JOSEException {
        // Parse token để chuyển thành SignedJWT
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Lấy JWTClaimsSet từ signedJWT
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        // Lấy giá trị của "subject"
        return claimsSet.getSubject();
    }


}




