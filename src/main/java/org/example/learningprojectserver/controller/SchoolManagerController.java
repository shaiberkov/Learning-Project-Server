package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.dto.UserDto;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.SchoolGradeService;
import org.example.learningprojectserver.service.SchoolManagerService;
import org.example.learningprojectserver.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Learning-App/School-Manager")
public class SchoolManagerController {

    private final SchoolGradeService schoolGradeService;
    private final SchoolManagerService schoolManagerService;
    private final SchoolService schoolService;

    @Autowired
    public SchoolManagerController(SchoolGradeService schoolGradeService, SchoolManagerService schoolManagerService, SchoolService schoolService) {
        this.schoolGradeService = schoolGradeService;
        this.schoolManagerService = schoolManagerService;
        this.schoolService = schoolService;
    }



    @PostMapping("/assign-user-as-school-teacher")
    public BasicResponse assignUserAsSchoolTeacher(@RequestParam String userId, @RequestParam String schoolCode) {
        return schoolManagerService.assignUserAsSchoolTeacher(userId,schoolCode);
    }

    @PostMapping("/remove-teacher-from-school")
    public BasicResponse removeTeacherFromSchool(@RequestParam String userId) {
        return schoolManagerService.removeTeacherFromSchool(userId);
    }

    @PostMapping("/add-school-grades")
    public BasicResponse addSchoolGrades(@RequestParam String schoolCode, @RequestBody List<String> gradesName) {
        return schoolManagerService.addSchoolGrades(schoolCode,gradesName);
    }

    @PostMapping("/remove-school-grades")
    public BasicResponse removeSchoolGrades(@RequestParam String schoolCode, @RequestBody List<String> gradesName) {
        return schoolManagerService.removeSchoolGrades(schoolCode, gradesName);
    }

    @PostMapping("/add-classes-to-grade")
    public BasicResponse addClassesToGrade(
            @RequestParam String schoolCode,
            @RequestParam String gradeName,
            @RequestParam int classesCount) {
        return schoolManagerService.addClassesToGrade(schoolCode, gradeName, classesCount);

    }

    @PostMapping("/add-additional-class-to-grade")
    public BasicResponse addAdditionalClassToGrade(
            @RequestParam String schoolCode,
            @RequestParam String gradeName,
            @RequestParam String className) {

        return schoolManagerService.addAdditionalClassToGrade(schoolCode, gradeName, className);

    }

    @PostMapping("/assign-teacher")
    public BasicResponse assignTeacherToClass(
            @RequestParam String schoolCode,
            @RequestParam String teacherId,
            @RequestParam String className) {
        return schoolManagerService.assignTeacherToClass(schoolCode, teacherId, className);
    }

    @PostMapping("/remove-teacher-from-class")
    public BasicResponse removeTeacherFromClass(@RequestParam String schoolCode,
                                                @RequestParam String teacherId,
                                                @RequestParam String className) {
        return schoolManagerService.removeTeacherFromClass(schoolCode, teacherId, className);
    }

    @GetMapping("/get-class-names-by-school-and-grade")
    public BasicResponse  getClassNamesBySchoolAndGrade(@RequestParam String schoolCode,@RequestParam String gradeName) {
        return schoolGradeService.getClassNamesBySchoolAndGrade(schoolCode,gradeName);
    }

    @GetMapping("/grades")
    public BasicResponse getGradesBySchoolCode(@RequestParam String schoolCode) {
        return schoolService.getGradesBySchoolCode(schoolCode);
    }





}
