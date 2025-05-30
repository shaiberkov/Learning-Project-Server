package org.example.learningprojectserver.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.learningprojectserver.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.example.learningprojectserver.constants.SecurityConstants.JwtAuthenticationFilterConstants.*;
import static org.example.learningprojectserver.constants.SharedConstants.SharedControllerConstants.AUTH_HEADER;
import static org.example.learningprojectserver.constants.SharedConstants.SharedControllerConstants.BEARER_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(AUTH_HEADER);
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(BEARER_PREFIX, "");

        if (!jwtService.isTokenValid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        String userId = jwtService.extractUserId(token);
        String role = jwtService.extractRole(token);
        String username = jwtService.extractUsername(token);

        Map<String, String> principal = Map.of(
                PRINCIPAL_USER_ID_KEY, userId,
                PRINCIPAL_USERNAME_KEY, username
        );

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                List.of(new SimpleGrantedAuthority(ROLE_PREFIX + role))
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
