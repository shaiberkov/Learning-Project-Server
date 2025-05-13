package org.example.learningprojectserver.config;

import org.example.learningprojectserver.filter.JwtAuthenticationFilter;
import org.example.learningprojectserver.filter.LoggingFilter;
import org.example.learningprojectserver.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }

    @Bean
    public LoggingFilter loggingFilter(JwtService jwtService) {
        return new LoggingFilter(jwtService);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   LoggingFilter loggingFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("/Learning-App/User/**").permitAll()
                        .requestMatchers("/Learning-App/validateToken/**").hasAnyRole("SYSTEM_ADMIN","TEACHER", "SCHOOLMANAGER","STUDENT")
                        .requestMatchers("/Learning-App/System-Admin/**").hasRole("SYSTEM_ADMIN")
                        .requestMatchers("/Learning-App/School-Manager/**").permitAll()    //hasAnyRole("SCHOOLMANAGER", "SYSTEM_ADMIN")
                        .requestMatchers("/Learning-App/Teacher/**").permitAll()    //hasAnyRole("TEACHER", "SCHOOLMANAGER", "SYSTEM_ADMIN")//todo  לבודד את ההרשאות
                        .requestMatchers("/Learning-App/Student/**").permitAll()//hasRole("STUDENT")//hasAnyRole("STUDENT")
                        .requestMatchers("/Learning-App/Active-User/**").hasAnyRole("SYSTEM_ADMIN","TEACHER", "SCHOOLMANAGER","STUDENT")
                        .requestMatchers("/Learning-App/Chat/**").hasAnyRole("SYSTEM_ADMIN","TEACHER", "SCHOOLMANAGER","STUDENT" )
                        .requestMatchers("/Learning-App/Question/**").hasAnyRole("TEACHER", "SCHOOLMANAGER","STUDENT")
                        .requestMatchers("/Learning-App/User-Statistic/**").hasAnyRole("TEACHER", "SCHOOLMANAGER","STUDENT")
                        .requestMatchers("/Learning-App/Self-Practice-Test/**").hasAnyRole("STUDENT")
                        .requestMatchers("/Learning-App/Message/**").permitAll()//hasAnyRole("TEACHER", "SCHOOLMANAGER", "SYSTEM_ADMIN")
                        .requestMatchers("/Learning-App/notifications/**").permitAll()
                        .requestMatchers("/Learning-App/Lesson/**").permitAll()
                        .requestMatchers("/Learning-App/Schedule/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}