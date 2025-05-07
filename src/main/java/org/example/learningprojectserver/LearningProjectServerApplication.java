package org.example.learningprojectserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;

@SpringBootApplication
@EnableScheduling
public class LearningProjectServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningProjectServerApplication.class, args);

    }

}
