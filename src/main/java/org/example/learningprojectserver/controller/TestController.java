package org.example.learningprojectserver.controller;



import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.example.learningprojectserver.constants.ControllerConstants.Test.GET_TEST;
import static org.example.learningprojectserver.constants.ControllerConstants.Test.TEST_BASE_PATH;

@RestController
@RequestMapping(TEST_BASE_PATH)
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;


    @GetMapping(GET_TEST)
    public TestDTO getTest(@RequestParam Long testId) {
        return testService.getTestDto(testId);
    }

}