package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.TeacherService;
import org.example.learningprojectserver.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Learning-App/Teacher")
public class TeacherController {

private final TeacherService teacherService;
private final TestResultService testResultService;

@Autowired
    public TeacherController(TeacherService teacherService, TestResultService testResultService) {
        this.teacherService = teacherService;
    this.testResultService = testResultService;
}


    @GetMapping("/schedule-for-teacher")
    public BasicResponse getScheduleForTeacher(@RequestParam String teacherId) {
        return teacherService.getTeacherSchedule(teacherId);
    }


    @PostMapping("/generate-test-for-students")
    public BasicResponse generateTestForStudents(
            @RequestBody List<String> usersIds,
            @RequestParam String teacherId,
            @RequestParam String testStartTime,
            @RequestParam String subject,
            @RequestParam String topic,
            @RequestParam String difficulty,
            @RequestParam int questionCount,
            @RequestParam int timeLimitMinutes) {

            return teacherService.generateTestForStudents(
                    usersIds,
                    teacherId,
                    testStartTime,
                    subject,
                    topic,
                    difficulty,
                    questionCount,
                    timeLimitMinutes
            );
        }
    @PostMapping("/check-teacher-test")
    public BasicResponse checkTeacherTest(
            @RequestParam String userId,
            @RequestParam Long testId,
            @RequestBody Map<Long, String> userAnswers) {
        return testResultService.checkTeacherTest(userId, testId, userAnswers);
    }

}
