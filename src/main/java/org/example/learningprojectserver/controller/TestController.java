package org.example.learningprojectserver.controller;



import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.entities.TestResultEntity;
import org.example.learningprojectserver.response.TestResultResponse;
import org.example.learningprojectserver.service.Tests.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Learning-App/Self-Practice-Test")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

//    @PostMapping("/generate-test")
//    public TestDTO generateTest(
//            @RequestParam String userName,
//            @RequestParam String subject,
//            @RequestParam String topic,
//            @RequestParam String difficulty,
//            @RequestParam int questionCount) {
//        return testService.generateTest(userName, subject, topic, difficulty, questionCount);
//    }



//    @PostMapping("/check-test")
//    public TestResultResponse getTestResults(
//            @RequestParam Long testId,
//            @RequestParam String userName,
//            @RequestBody Map<Long, String> userAnswers) {
//        return testService.getTestResultResponse(userName, testId, userAnswers);
//    }
}