package org.example.learningprojectserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.TokenValidationResponse;
import org.example.learningprojectserver.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Learning-App/validateToken")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

//    @PostMapping("/validateToken")
//    public ResponseEntity<TokenValidationResponse> validateToken(@RequestHeader("Authorization") String token) {
//        if (token == null || token.isEmpty()) {
//            System.out.println("Token missing");
//            return ResponseEntity.badRequest().body(new TokenValidationResponse(false, "Token is missing", false,null));
//        }
//
//        String cleanToken = token.replace("Bearer ", "");
//
//        TokenValidationResponse response = sessionService.validateToken(cleanToken);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/validateToken")
    public TokenValidationResponse validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        System.out.println(sessionService.validateToken(token));
        return sessionService.validateToken(token);
    }



}