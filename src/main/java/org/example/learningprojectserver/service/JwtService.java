package org.example.learningprojectserver.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.example.learningprojectserver.enums.Role;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final String SECRET = String.valueOf(Keys.secretKeyFor(SignatureAlgorithm.HS256)); // שים במשתנה סביבה

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // שעה

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

//   @PostConstruct
//   public void init() {
//       System.out.println( "מנהל");
//       System.out.println("אדמיניסטראטור"+generateToken("325256022", String.valueOf(Role.SYSTEM_ADMIN)));
//       System.out.println( extractUserId(generateToken("325256022", String.valueOf(Role.SCHOOLMANAGER))));
//   }

    public String generateToken(String userId, String username,String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public String extractUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }


    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
