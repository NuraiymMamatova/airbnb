package com.example.airbnbb7.authwithgoogle.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtils {

    @Value("airbnb")
    private String jwtSecret;

    private final static Long JWT_TOKEN_VALIDATION = 7 * 24 * 60 * 70 * 1000L;

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(
                        new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDATION))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String generateToken(String username) {
        Map<String, Object> objectMap = new HashMap<>();
        return createToken(objectMap, username);
    }

}
