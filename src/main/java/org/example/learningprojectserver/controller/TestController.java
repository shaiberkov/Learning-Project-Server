package org.example.learningprojectserver.controller;



import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Learning-App/Test")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }
    @GetMapping("/get-test")
    public TestDTO getTest(@RequestParam Long testId) {
        return testService.getTestDto(testId);
    }

}