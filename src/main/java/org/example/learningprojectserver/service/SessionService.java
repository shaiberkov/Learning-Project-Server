package org.example.learningprojectserver.service;


import org.example.learningprojectserver.repository.SessionRepository;
import org.example.learningprojectserver.response.TokenValidationResponse;
import org.example.learningprojectserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final JwtService jwtService;

    @Autowired
    public SessionService(SessionRepository sessionRepository, JwtService jwtService) {
        this.sessionRepository = sessionRepository;
        this.jwtService = jwtService;
    }

    public TokenValidationResponse validateToken(String cleanToken) {
        boolean isValid = jwtService.isTokenValid(cleanToken);
        String username = "";
        String userId = "";
        String role = "";
        if (isValid) {
            username = jwtService.extractUsername(cleanToken);
            userId = jwtService.extractUserId(cleanToken);
            role = jwtService.extractRole(cleanToken);
        }

        TokenValidationResponse tokenValidationResponse = new TokenValidationResponse();
        tokenValidationResponse.setValid(isValid);
        tokenValidationResponse.setUsername(username);
        tokenValidationResponse.setUserId(userId);
        tokenValidationResponse.setRole(role);
        return tokenValidationResponse;
    }


}