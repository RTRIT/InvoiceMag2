package com.example.invoiceProject.Config;

import java.text.ParseException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import com.example.invoiceProject.DTO.requests.IntrospectRequest;
import com.example.invoiceProject.Service.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import com.nimbusds.jose.JOSEException;

// Create own customJwtDecoder
//Using Nimbus for signed and validate Jwts in Java

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.secret}")
    private String signerKey;



    @Autowired
    private AuthenticateService authenticateService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    // Overide decode to use NimbusJwtDecoder for decode, verify, validate token
    @Override
    public Jwt decode(String token) throws JwtException {

        // Check the current token is valid
        try {
            var response = authenticateService.introspect(
                    IntrospectRequest.builder().token(token).build());

            if (!response.isValid()) throw new JwtException("Token invalid");
        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }

        //JwtDecoder will decode, verify and validate token
        //Initialize a NimbusJwtDecoder with HMAC-SHA-512 algorithm and the secret key
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        // Return Jwt object if the token is valid
//        System.out.println(nimbusJwtDecoder.decode(token));

        return nimbusJwtDecoder.decode(token);
    }
}
