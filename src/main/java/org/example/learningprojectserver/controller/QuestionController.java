package org.example.learningprojectserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.QuestionDTO;
import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.response.SubmitAnswerResponse;
import org.example.learningprojectserver.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.example.learningprojectserver.constants.ControllerConstants.Question.QUESTION_BASE_PATH;

@RestController(QUESTION_BASE_PATH)
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


//    @GetMapping("/generate-question")
//    public QuestionDTO generateQuestion(
//            @RequestParam String userName,
//            @RequestParam String subject,
//            @RequestParam String topic,
//            @RequestParam String subTopic
//            ) {
//        return questionService.generateQuestion(userName,subject, topic, subTopic);
//    }
//
//    @PostMapping("/submit-answer")
//    public SubmitAnswerResponse submitAnswer(
//            @RequestParam String userName,
//            @RequestParam Long id,
//            @RequestParam String subject,
//            @RequestParam String topic,
//            @RequestParam String subTopic,
//            @RequestParam String questionText,
//            @RequestParam String answer
//    ) {
//        return questionService.submitAnswer(userName, id,subject, topic, subTopic, questionText, answer);
//    }

}
