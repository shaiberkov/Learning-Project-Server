package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.dto.LessonDTO;
import org.example.learningprojectserver.dto.UserDto;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Learning-App/School-Manager")
public class SchoolManagerController {
private final ClassRoomService classRoomService;
    private final SchoolGradeService schoolGradeService;
    private final SchoolManagerService schoolManagerService;
    private final SchoolService schoolService;
    private final TeacherService teacherService;


    @Autowired
    public SchoolManagerController(ClassRoomService classRoomService, SchoolGradeService schoolGradeService, SchoolManagerService schoolManagerService, SchoolService schoolService, TeacherService teacherService) {
        this.classRoomService = classRoomService;
        this.schoolGradeService = schoolGradeService;
        this.schoolManagerService = schoolManagerService;
        this.schoolService = schoolService;
        this.teacherService = teacherService;
    }

    @GetMapping("/get-teacher-DTO")
    public BasicResponse getTeacherDTO(@RequestParam String teacherId,@RequestParam String schoolCode) {
        return teacherService.getTeacherDTO(teacherId,schoolCode);
    }

    @GetMapping("/get-all-classes-name-by-school-code")
    public BasicResponse getAllClassesNameBySchoolCode(@RequestParam String schoolCode) {
        return schoolService.getAllClassesNameBySchoolCode(schoolCode);
    }

    @GetMapping("/get-school-code")
    public BasicResponse getSchoolCode(@RequestParam String userId){
        return schoolManagerService.getSchoolCode(userId);

    }

@GetMapping("/get-schedule-for-classRoom")
public BasicResponse getAllLessonsForClassRoom(@RequestParam String schoolCode , @RequestParam String classRoomName) {
        return classRoomService.getScheduleForClassRoom(schoolCode, classRoomName);

}

    @PostMapping("/assign-user-as-school-teacher")
    public BasicResponse assignUserAsSchoolTeacher(@RequestParam String userId, @RequestParam String schoolCode) {
        return schoolManagerService.assignUserAsSchoolTeacher(userId, schoolCode);
    }

    @PostMapping("/remove-teacher-from-school")
    public BasicResponse removeTeacherFromSchool(@RequestParam String userId) {
        return schoolManagerService.removeTeacherFromSchool(userId);
    }

    @PostMapping("/add-school-grades")
    public BasicResponse addSchoolGrades(@RequestParam String schoolCode, @RequestBody List<String> gradesName) {
        return schoolManagerService.addSchoolGrades(schoolCode, gradesName);
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

    @PostMapping("/assign-teacher-to-classes")
    public BasicResponse assignTeacherToClasses(
            @RequestParam String schoolCode,
            @RequestParam String teacherId,
            @RequestBody List<String> classNames) {
        return schoolManagerService.assignTeacherToClasses(schoolCode, teacherId, classNames);
    }

    @PostMapping("/remove-teacher-from-class")
    public BasicResponse removeTeacherFromClass(@RequestParam String schoolCode,
                                                @RequestParam String teacherId,
                                                @RequestParam String className) {
        return schoolManagerService.removeTeacherFromClass(schoolCode, teacherId, className);
    }

    @GetMapping("/get-class-names-by-school-and-grade")
    public BasicResponse getClassNamesBySchoolAndGrade(@RequestParam String schoolCode, @RequestParam String gradeName) {
        return schoolGradeService.getClassNamesBySchoolAndGrade(schoolCode, gradeName);
    }

    @GetMapping("/grades")
    public BasicResponse getGradesBySchoolCode(@RequestParam String schoolCode) {
        return schoolService.getGradesBySchoolCode(schoolCode);
    }

    @PostMapping("/add-lesson-to-teacher")
    public BasicResponse addLessonToTeacher(@RequestBody LessonDTO dto, @RequestParam String teacherId) {
        return teacherService.addLessonToTeacher(dto, teacherId);

    }

    @PostMapping("/add-teaching-subject-to-teacher")
    public BasicResponse addTeachingSubjectToTeacher(@RequestParam String teacherId, @RequestBody List<String> subjects) {
        return teacherService.addTeachingSubjectToTeacher(teacherId, subjects);

    }

    @PostMapping("/remove-teaching-subject-from-teacher")
    public BasicResponse removeTeachingSubjectFromTeacher(@RequestParam String teacherId, @RequestParam String subjectToRemove) {
        return teacherService.removeTeachingSubjectFromTeacher(teacherId, subjectToRemove);
    }

    @PostMapping("/assign-student-to-class")
    public BasicResponse assignStudentToClass(
            @RequestParam String schoolCode,
            @RequestParam String studentId,
            @RequestParam String className
    ) {
        return schoolManagerService.assignStudentToClass(schoolCode, studentId, className);
    }

    @PostMapping("/change-student-class")
    public BasicResponse changeStudentClass(
            @RequestParam String schoolCode,
            @RequestParam String studentId,
            @RequestParam String gradeName,
            @RequestParam String newClassName
    ) {
        return schoolManagerService.changeStudentClass(schoolCode, studentId, gradeName, newClassName);
    }
    @GetMapping("/get-all-teachers")
    public BasicResponse getAllTeachers(@RequestParam String schoolCode) {
        return schoolManagerService.getAllTeachers(schoolCode);
    }

    @GetMapping("/get-all-students")
    public BasicResponse getAllStudents(@RequestParam String schoolCode) {
        return schoolManagerService.getAllStudents(schoolCode);
    }

    @GetMapping("/get-teacher-teaching-subjects")
    public BasicResponse getTeacherTeachingSubjects(@RequestParam String teacherId) {
        return teacherService.getTeacherTeachingSubjects(teacherId);
    }


}







