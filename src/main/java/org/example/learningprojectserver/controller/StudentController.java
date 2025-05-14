package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.dto.QuestionDTO;
import org.example.learningprojectserver.dto.UserTestStatusDTO;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.SubmitAnswerResponse;
import org.example.learningprojectserver.service.StudentService;
import org.example.learningprojectserver.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Learning-App/Student")
public class StudentController {

private final StudentService studentService;
private final TestResultService testResultService;


    @Autowired
    public StudentController(StudentService studentService, TestResultService testResultService) {
        this.studentService = studentService;
        this.testResultService = testResultService;
    }


    @GetMapping("/get-student-schedule")
    public BasicResponse getStudentSchedule(@RequestParam String schoolCode,@RequestParam String studentId){
        return studentService.getStudentSchedule(schoolCode,studentId);
    }

    @GetMapping("/generate-question")
    public BasicResponse generateQuestionForPractice(
            @RequestParam String userId,
            @RequestParam String subject,
            @RequestParam String topic,
            @RequestParam String subTopic) {
        return studentService.generateQuestionForPractice(userId, subject, topic, subTopic);
    }

    @PostMapping("/submit-answer")
    public BasicResponse submitAnswer(
            @RequestParam String userId,
            @RequestParam Long id,
            @RequestParam String subTopic,
            @RequestParam String answer) {
        return studentService.submitAnswer(userId,id,subTopic,answer);
    }
    @PostMapping("/generate-practice-test")
    public BasicResponse generatePracticeTest(
            @RequestParam String userId,
            @RequestParam String subject,
            @RequestParam String topic,
            @RequestParam String difficulty,
            @RequestParam int questionCount,
            @RequestParam int timeLimitMinutes) {

        return studentService.generatePracticeTest(userId, subject, topic, difficulty, questionCount, timeLimitMinutes);
    }

    @PostMapping("/check-practice-test")
    public BasicResponse checkPracticeTest(
            @RequestParam String userId,
            @RequestParam Long testId,
            @RequestBody Map<Long, String> userAnswers) {

        return testResultService.checkPracticeTest(userId, testId, userAnswers);
    }

    @PostMapping("/check-teacher-test")
    public BasicResponse checkTeacherTest(
            @RequestParam String userId,
            @RequestParam Long testId,
            @RequestBody Map<Long, String> userAnswers) {

        return testResultService.checkTeacherTest(userId,testId, userAnswers);
    }

    @PostMapping("/start-test")
    public BasicResponse startTest(
            @RequestParam String userId,
            @RequestParam Long testId) {
        return testResultService.startTest(testId,userId);
    }

    @GetMapping("/get-student-tests-status")
    public List<UserTestStatusDTO> getStudentTestsStatus(@RequestParam String studentId){
        return studentService.getStudentTestsStatus(studentId);
    }


}
