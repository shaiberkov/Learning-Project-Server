package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/generate-question")
    public QuestionEntity generateQuestion(
            @RequestParam String subject,
            @RequestParam String topic,
            @RequestParam String subTopic,
            @RequestParam int difficulty) {
        return questionService.generateQuestion(subject, topic, subTopic, difficulty);
    }
}
