package org.example.learningprojectserver.controller;


import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.MathQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathQuestionController {

    private MathQuestionService questionService;

    @Autowired
    public MathQuestionController(MathQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/generate-Math-question")
    public QuestionEntity generateMathQuestion(
            @RequestParam String subTopic,
            @RequestParam int difficulty) {
        return questionService.generateMathQuestion(subTopic, difficulty);
    }
}