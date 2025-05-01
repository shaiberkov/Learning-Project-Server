package org.example.learningprojectserver.config;

import org.example.learningprojectserver.filter.JwtAuthenticationFilter;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
@Autowired
    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Learning-App/User/**").permitAll()
                        .requestMatchers("/Learning-App/System-Admin/**").permitAll()//hasRole("SYSTEM_ADMIN")
                        .requestMatchers("/Learning-App/School-Manager/**").permitAll()    //hasAnyRole("SCHOOLMANAGER", "SYSTEM_ADMIN")
                        .requestMatchers("/Learning-App/Teacher/**").permitAll()    //hasAnyRole("TEACHER", "SCHOOLMANAGER", "SYSTEM_ADMIN")//todo  לבודד את ההרשאות
                        .requestMatchers("/Learning-App/Student/**").hasAnyRole("STUDENT")
                        .requestMatchers("/Learning-App/Active-User/**").hasAnyRole("SYSTEM_ADMIN","TEACHER", "SCHOOLMANAGER","STUDENT")
                        .requestMatchers("/Learning-App/Chat/**").hasAnyRole("SYSTEM_ADMIN","TEACHER", "SCHOOLMANAGER","STUDENT" )
                        .requestMatchers("/Learning-App/Question/**").hasAnyRole("TEACHER", "SCHOOLMANAGER","STUDENT")
                        .requestMatchers("/Learning-App/User-Statistic/**").hasAnyRole("TEACHER", "SCHOOLMANAGER","STUDENT")
                        .requestMatchers("/Learning-App/Self-Practice-Test/**").hasAnyRole("STUDENT")
                        .requestMatchers("/Learning-App/Message/**").permitAll()//hasAnyRole("TEACHER", "SCHOOLMANAGER", "SYSTEM_ADMIN")
                        .requestMatchers("/Learning-App/notifications/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}