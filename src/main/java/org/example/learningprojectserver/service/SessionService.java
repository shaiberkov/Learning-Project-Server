package org.example.learningprojectserver.service;


import org.example.learningprojectserver.repository.SessionRepository;
import org.example.learningprojectserver.response.TokenValidationResponse;
import org.example.learningprojectserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public TokenValidationResponse validateToken(String cleanToken) {
        boolean isValid = JwtUtils.isTokenValid(cleanToken);
        String username = "";
        if (isValid){
            username = JwtUtils.extractUsername(cleanToken);
        }

        return new TokenValidationResponse(isValid, isValid ? "Token is valid" : "Token is invalid", isValid,username);
    }

}