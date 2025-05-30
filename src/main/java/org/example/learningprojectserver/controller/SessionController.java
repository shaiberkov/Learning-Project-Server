package org.example.learningprojectserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.TokenValidationResponse;
import org.example.learningprojectserver.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.learningprojectserver.constants.ControllerConstants.Session.*;
import static org.example.learningprojectserver.constants.SharedConstants.SharedControllerConstants.AUTH_HEADER;
import static org.example.learningprojectserver.constants.SharedConstants.SharedControllerConstants.BEARER_PREFIX;

@RestController
@RequestMapping(SESSION_BASE_PATH)
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;


    @GetMapping(VALIDATE_TOKEN)
    public TokenValidationResponse validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER);

        String token = null;
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            token = authHeader.substring(BEARER_PREFIX.length());
        }
        System.out.println(sessionService.validateToken(token));
        return sessionService.validateToken(token);
    }



}