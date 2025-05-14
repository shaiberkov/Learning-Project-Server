package org.example.learningprojectserver.service;


import org.example.learningprojectserver.repository.SchoolManagerRepository;
import org.example.learningprojectserver.repository.SessionRepository;
import org.example.learningprojectserver.repository.StudentRepository;
import org.example.learningprojectserver.repository.TeacherRepository;
import org.example.learningprojectserver.response.TokenValidationResponse;
import org.example.learningprojectserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final JwtService jwtService;
    private final SchoolManagerRepository schoolManagerRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    //todo לנקות את המטמון
    private final Map<String, TokenValidationResponse> tokenCache = new ConcurrentHashMap<>();



    @Autowired
    public SessionService(SessionRepository sessionRepository, JwtService jwtService, SchoolManagerRepository schoolManagerRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.sessionRepository = sessionRepository;
        this.jwtService = jwtService;
        this.schoolManagerRepository = schoolManagerRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
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