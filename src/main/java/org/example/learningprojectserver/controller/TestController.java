package org.example.learningprojectserver.controller;



import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Learning-App/Self-Practice-Test")
public class TestController {

//    private final TestService testService;
//
//    @Autowired
//    public TestController(TestService testService) {
//        this.testService = testService;
//    }
//
//    @PostMapping("/generate-test")
//    public TestDTO generateTest(
//            @RequestParam String userName,
//            @RequestParam String subject,
//            @RequestParam String topic,
//            @RequestParam String difficulty,
//            @RequestParam int questionCount) {
//        return testService.generateTest(userName, subject, topic, difficulty, questionCount);
//    }
//
//    @PostMapping("/generate-test")
//    public BasicResponse



//    @PostMapping("/check-test")
//    public TestResultResponse getTestResults(
//            @RequestParam Long testId,
//            @RequestParam String userName,
//            @RequestBody Map<Long, String> userAnswers) {
//        return testService.getTestResultResponse(userName, testId, userAnswers);
//    }
}