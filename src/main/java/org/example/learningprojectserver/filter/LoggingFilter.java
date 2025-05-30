package org.example.learningprojectserver.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.learningprojectserver.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.example.learningprojectserver.constants.SecurityConstants.JwtAuthenticationFilterConstants.PRINCIPAL_USERNAME_KEY;
import static org.example.learningprojectserver.constants.SecurityConstants.JwtAuthenticationFilterConstants.PRINCIPAL_USER_ID_KEY;
import static org.example.learningprojectserver.constants.SecurityConstants.LoggingFilterConstants.*;
import static org.example.learningprojectserver.constants.SharedConstants.SharedControllerConstants.AUTH_HEADER;
import static org.example.learningprojectserver.constants.SharedConstants.SharedControllerConstants.BEARER_PREFIX;


public class LoggingFilter extends OncePerRequestFilter {


    private final JwtService jwtService;

    public LoggingFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();

        // נשלוף את הטוקן מה-Header
        String authHeader = request.getHeader(AUTH_HEADER);
        String token = null;
        String userId = UNKNOWN_USER_ID;
        String username = UNKNOWN_USERNAME;
        String role = UNKNOWN_ROLE;

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            token = authHeader.substring(BEARER_PREFIX.length());
            try {
                if (jwtService.isTokenValid(token)) {
                    userId = jwtService.extractUserId(token);
                    username = jwtService.extractUsername(token);
                    role = jwtService.extractRole(token);
                }
            } catch (Exception e) {
                System.out.println(JWT_DECODE_ERROR_PREFIX + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);

        int status = response.getStatus();
//        String logMessage = String.format(
//                "[%s] %s %s -> %d | userId: %s | username: %s | role: %s | ip: %s",
//                LocalDateTime.now(), method, uri, status, userId, username, role, ip
//        );
        String logMessage = String.format(
                LOG_FORMAT,
                LocalDateTime.now(), method, uri, status,
                PRINCIPAL_USER_ID_KEY, userId,
                PRINCIPAL_USERNAME_KEY, username,
                role, ip
        );

        System.out.println(logMessage);

        // אופציונלית: כתיבה לקובץ לוג
        // FileLogger.writeLogToFile(logMessage);
    }
}
