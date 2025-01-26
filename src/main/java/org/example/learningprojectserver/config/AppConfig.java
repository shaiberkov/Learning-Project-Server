package org.example.learningprojectserver.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // מאפשר לכל ה-Endpoints
                        .allowedOrigins("*")  // מאפשר גישה מכל מקור
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // מאפשר בקשות בשיטות שונות
                        .allowedHeaders("*");  // מאפשר כל Header בבקשות
            }
        };
    }
}