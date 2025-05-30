package org.example.learningprojectserver.config;

import org.example.learningprojectserver.filter.JwtAuthenticationFilter;
import org.example.learningprojectserver.filter.LoggingFilter;
import org.example.learningprojectserver.filter.MetricsFilter;
import org.example.learningprojectserver.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.cache.CacheManager;


import java.util.List;

import static org.example.learningprojectserver.constants.SecurityConstants.SecurityMatcherConstants.*;

@Configuration
@EnableAsync
@EnableCaching
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
    public MetricsFilter metricsFilter() {
        return new MetricsFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   LoggingFilter loggingFilter,
                                                   MetricsFilter metricsFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                V3_API_DOCS,
                                SWAGGER_UI_RESOURCES,
                                SWAGGER_UI_HTML
                        ).permitAll()
                        .requestMatchers(USER_ALL).permitAll()
                        .requestMatchers(VALIDATE_TOKEN_ALL).permitAll()//.hasAnyRole("SYSTEM_ADMIN","TEACHER", "SCHOOLMANAGER","STUDENT")
                        .requestMatchers(SYSTEM_ADMIN_ALL).permitAll()//.hasRole("SYSTEM_ADMIN")
                        .requestMatchers(SCHOOL_MANAGER_ALL).permitAll()    //hasAnyRole("SCHOOLMANAGER", "SYSTEM_ADMIN")
                        .requestMatchers(TEACHER_ALL).permitAll()    //hasAnyRole("TEACHER", "SCHOOLMANAGER", "SYSTEM_ADMIN")//todo  לבודד את ההרשאות
                        .requestMatchers(STUDENT_ALL).permitAll()//hasRole("STUDENT")//hasAnyRole("STUDENT")
                        .requestMatchers(ACTIVE_USER_ALL).permitAll()//.hasAnyRole("SYSTEM_ADMIN","TEACHER", "SCHOOLMANAGER","STUDENT")
                        .requestMatchers(CHAT_ALL).permitAll()//hasAnyRole("SYSTEM_ADMIN","TEACHER", "SCHOOLMANAGER","STUDENT" )
                        .requestMatchers(QUESTION_ALL).permitAll()//.hasAnyRole("TEACHER", "SCHOOLMANAGER","STUDENT")
                        .requestMatchers(USER_STATISTIC_ALL).permitAll()//.hasAnyRole("TEACHER", "SCHOOLMANAGER","STUDENT")
                        .requestMatchers(TEST_ALL).permitAll()//hasAnyRole("STUDENT")
                        .requestMatchers(MESSAGE_ALL).permitAll()//hasAnyRole("TEACHER", "SCHOOLMANAGER", "SYSTEM_ADMIN")
                        .requestMatchers(NOTIFICATION_ALL).permitAll()
                        .requestMatchers(LESSON_ALL).permitAll()
                        .requestMatchers(SCHEDULE_ALL).permitAll()
                        .requestMatchers(UPCOMING_EVENTS_ALL).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(metricsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        configuration.setAllowedMethods(ALLOWED_METHODS);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(CORS_MAPPING_PATH, configuration);
        return source;
    }


}