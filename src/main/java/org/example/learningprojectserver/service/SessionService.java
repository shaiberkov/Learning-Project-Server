package org.example.learningprojectserver.service;


import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.repository.SchoolManagerRepository;
import org.example.learningprojectserver.repository.StudentRepository;
import org.example.learningprojectserver.repository.TeacherRepository;
import org.example.learningprojectserver.response.TokenValidationResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final JwtService jwtService;
    private final SchoolManagerRepository schoolManagerRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final Map<String, TokenValidationResponse> tokenCache = new ConcurrentHashMap<>();



    @Scheduled(fixedRate = 7_200_000)
    public void clearTokenCache() {
        tokenCache.clear();
    }

    public TokenValidationResponse validateToken(String cleanToken) {

        if (tokenCache.containsKey(cleanToken)) {
            return tokenCache.get(cleanToken);
        }
        boolean isValid = jwtService.isTokenValid(cleanToken);
        String username = "";
        String userId = "";
        String role = "";
        if (isValid) {
            username = jwtService.extractUsername(cleanToken);
            userId = jwtService.extractUserId(cleanToken);
            role = jwtService.extractRole(cleanToken);
        }
        String schoolCode="";
        switch (role.toUpperCase()) {
            case "STUDENT":
                schoolCode = studentRepository.findSchoolCodeByUserId(userId);
                break;
            case "TEACHER":
                schoolCode=teacherRepository.findSchoolCodeByUserId(userId);
                break;
            case "SCHOOLMANAGER":
                schoolCode= schoolManagerRepository.findSchoolCodeByUserId(userId);
                break;
            default:
                System.out.println("תפקיד לא מוכר: " + role);
        }


        TokenValidationResponse tokenValidationResponse = new TokenValidationResponse();
        tokenValidationResponse.setValid(isValid);
        tokenValidationResponse.setUsername(username);
        tokenValidationResponse.setUserId(userId);
        tokenValidationResponse.setRole(role);
        if (!"SYSTEM_ADMIN".equals(role)) {
            tokenValidationResponse.setSchoolCode(schoolCode);
        }

        tokenCache.put(cleanToken, tokenValidationResponse);

        return tokenValidationResponse;
    }


}