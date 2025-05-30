package org.example.learningprojectserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.StudentTestStatusDTO;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.StudentService;
import org.example.learningprojectserver.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.example.learningprojectserver.constants.ControllerConstants.Student.*;

@RestController
@RequestMapping(STUDENT_BASE_PATH)
@RequiredArgsConstructor
public class StudentController {

private final StudentService studentService;
private final TestResultService testResultService;



    @GetMapping(GET_STUDENT_SCHEDULE)
    public BasicResponse getStudentSchedule(@RequestParam String schoolCode,@RequestParam String studentId){
        return studentService.getStudentSchedule(schoolCode,studentId);
    }

    @GetMapping(GENERATE_QUESTION)
    public BasicResponse generateQuestionForPractice(
            @RequestParam String userId,
            @RequestParam String subject,
            @RequestParam String topic,
            @RequestParam String subTopic) {
        return studentService.generateQuestionForPractice(userId, subject, topic, subTopic);
    }

    @PostMapping(SUBMIT_ANSWER)
    public BasicResponse submitAnswer(
            @RequestParam String userId,
            @RequestParam Long id,
            @RequestParam String subTopic,
            @RequestParam String answer) {
        return studentService.submitAnswer(userId,id,subTopic,answer);
    }
    @PostMapping(GENERATE_PRACTICE_TEST)
    public BasicResponse generatePracticeTest(
            @RequestParam String userId,
            @RequestParam String subject,
            @RequestParam String topic,
            @RequestParam String difficulty,
            @RequestParam int questionCount,
            @RequestParam int timeLimitMinutes) {

        return studentService.generatePracticeTest(userId, subject, topic, difficulty, questionCount, timeLimitMinutes);
    }

    @PostMapping(CHECK_PRACTICE_TEST)
    public BasicResponse checkPracticeTest(
            @RequestParam String userId,
            @RequestParam Long testId,
            @RequestBody Map<Long, String> userAnswers) {

        return testResultService.checkPracticeTest(userId, testId, userAnswers);
    }



    @PostMapping(START_TEST)
    public BasicResponse startTest(
            @RequestParam String userId,
            @RequestParam Long testId) {
        return testResultService.startTest(testId,userId);
    }

    @GetMapping(GET_STUDENT_TESTS_STATUS)
    public List<StudentTestStatusDTO> getStudentTestsStatus(@RequestParam String studentId){
        return studentService.getStudentTestsStatus(studentId);
    }


}
