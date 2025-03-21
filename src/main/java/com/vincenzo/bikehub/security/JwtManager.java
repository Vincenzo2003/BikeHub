package com.vincenzo.bikehub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtManager {

    private final SecretKey secretKey;
    private final String issuer;
    private final long expirationTime = 3600000;

    @Autowired
    public JwtManager(
            @Value("${spring.application.secret}") String secret,
            @Value("${spring.application.name}") String issuer
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.issuer = issuer;
    }

    public String generateToken(String subject, String role) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .issuer(issuer)
                .subject(subject)
                .issuedAt(now)
                .expiration(expirationDate)
                .claim("role", role)
                .signWith(secretKey)
                .compact();
    }

    public Claims validateToken(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}