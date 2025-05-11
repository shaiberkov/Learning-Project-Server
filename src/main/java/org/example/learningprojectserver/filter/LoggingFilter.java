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

@Component
public class LoggingFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
@Autowired
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
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userId = "UNKNOWN";
        String username = "UNKNOWN";
        String role = "UNKNOWN";

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                if (jwtService.isTokenValid(token)) {
                    userId = jwtService.extractUserId(token);
                    username = jwtService.extractUsername(token);
                    role = jwtService.extractRole(token);
                }
            } catch (Exception e) {
                System.out.println("שגיאה בפענוח JWT: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);

        int status = response.getStatus();
        String logMessage = String.format(
                "[%s] %s %s -> %d | userId: %s | username: %s | role: %s | ip: %s",
                LocalDateTime.now(), method, uri, status, userId, username, role, ip
        );

        // הדפסה לקונסול
        System.out.println(logMessage);

        // אופציונלית: כתיבה לקובץ לוג
        // FileLogger.writeLogToFile(logMessage);
    }
}
