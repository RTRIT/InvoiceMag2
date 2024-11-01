////Ensures we use secrets of the proper strength for the given algorithm
//package com.example.invoiceProject.Service.JwtService;
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwsHeader;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.SigningKeyResolver;
//import io.jsonwebtoken.SigningKeyResolverAdapter;
//
//import io.jsonwebtoken.lang.Assert;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Service;
//
////import javax.annotation.PostConstruct;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Base64;
//
//
//
//@Service
//public class SecretService {
//
//    private Map<String, String> secrets = new HashMap<>();
//
//    private final SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
//
//        @Override
//        public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
//            String algorithm = header.getAlgorithm();
//            System.out.println("Algorithm in header: " + algorithm);
//            return Base64.getDecoder().decode(secrets.get(header.getAlgorithm()));
//        }
//    };
//
//    @PostConstruct
//    public void setup() throws NoSuchAlgorithmException {
//        refreshSecrets();
//        System.out.println(secrets);
//    }
//
//    public SigningKeyResolver getSigningKeyResolver() {
//        return signingKeyResolver;
//    }
//
//    public Map<String, String> getSecrets() {
//        return secrets;
//    }
//
//    public void setSecrets(Map<String, String> secrets) {
//        Assert.notNull(secrets);
//        Assert.hasText(secrets.get("HS256"));
//        Assert.hasText(secrets.get("HS384"));
//        Assert.hasText(secrets.get("HS512"));
//
//        this.secrets = secrets;
//    }
//
//    public byte[] getHS256SecretBytes() {
//        return Base64.getDecoder().decode(secrets.get("HS256"));
//    }
//
//    public byte[] getHS384SecretBytes() {
//        return Base64.getDecoder().decode(secrets.get("HS384"));
//    }
//
//    public byte[] getHS512SecretBytes() {
//        return Base64.getDecoder().decode(secrets.get("HS512"));
//    }
//
//    public Map<String, String> refreshSecrets() throws NoSuchAlgorithmException {
//        SecretKey key = KeyGenerator.getInstance(SignatureAlgorithm.HS256.getJcaName()).generateKey();
//        secrets.put("HS256", Base64.getEncoder().encodeToString(key.getEncoded()));
////        secrets.put(SignatureAlgorithm.HS256.getJcaName(), Base64.getEncoder().encodeToString(key.getEncoded()));
//
//
//        key =  KeyGenerator.getInstance(SignatureAlgorithm.HS384.getJcaName()).generateKey();
//        secrets.put("HS384", Base64.getEncoder().encodeToString(key.getEncoded()));
////        secrets.put(SignatureAlgorithm.HS384.getJcaName(), Base64.getEncoder().encodeToString(key.getEncoded()));
//
//
//        key = KeyGenerator.getInstance(SignatureAlgorithm.HS512.getJcaName()).generateKey();
//        secrets.put("HS512", Base64.getEncoder().encodeToString(key.getEncoded()));
//        secrets.put(SignatureAlgorithm.HS512.getJcaName(), Base64.getEncoder().encodeToString(key.getEncoded()));
//        return secrets;
//    }
//}