package com.pr0f1t.Bazario.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        return isTokenValid(token);
    }

    public String extractUserRole(String token) {
        try {
            Claims claims = jwtParser()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.get("role", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isTokenValid(String token) {
        try {
            jwtParser().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private JwtParser jwtParser() {
        SecretKeySpec secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), jwtProperties.getAlgorithm());
        return Jwts.parser()
                .verifyWith(secretKey)
                .requireIssuer(jwtProperties.getIssuer())
                .requireAudience(jwtProperties.getAudience())
                .build();
    }
}
