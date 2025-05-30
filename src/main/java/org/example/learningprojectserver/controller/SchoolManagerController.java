package org.example.learningprojectserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.LessonDTO;
import org.example.learningprojectserver.dto.UserDto;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.learningprojectserver.constants.ControllerConstants.SchoolManager.*;

@RestController
@RequestMapping(SCHOOL_MANAGER_BASE_PATH)
@RequiredArgsConstructor
public class SchoolManagerController {
private final ClassRoomService classRoomService;
    private final SchoolGradeService schoolGradeService;
    private final SchoolManagerService schoolManagerService;
    private final SchoolService schoolService;
    private final TeacherService teacherService;


    @GetMapping(GET_TEACHER_DTO)
    public BasicResponse getTeacherDTO(@RequestParam String teacherId,@RequestParam String schoolCode) {
        return teacherService.getTeacherDTO(teacherId,schoolCode);
    }

    @GetMapping(GET_ALL_CLASSES_BY_SCHOOL_CODE)
    public BasicResponse getAllClassesNameBySchoolCode(@RequestParam String schoolCode) {
        return schoolService.getAllClassesNameBySchoolCode(schoolCode);
    }

    @GetMapping(GET_SCHOOL_CODE)
    public BasicResponse getSchoolCode(@RequestParam String userId){
        return schoolManagerService.getSchoolCode(userId);

    }

@GetMapping(GET_SCHEDULE_FOR_CLASSROOM)
public BasicResponse getAllLessonsForClassRoom(@RequestParam String schoolCode , @RequestParam String classRoomName) {
        return classRoomService.getScheduleForClassRoom(schoolCode, classRoomName);

}

    @PostMapping(ASSIGN_USER_AS_SCHOOL_TEACHER)
    public BasicResponse assignUserAsSchoolTeacher(@RequestParam String userId, @RequestParam String schoolCode) {
        return schoolManagerService.assignUserAsSchoolTeacher(userId, schoolCode);
    }

    @PostMapping(REMOVE_TEACHER_FROM_SCHOOL)
    public BasicResponse removeTeacherFromSchool(@RequestParam String userId) {
        return schoolManagerService.removeTeacherFromSchool(userId);
    }

    @PostMapping(ADD_SCHOOL_GRADES)
    public BasicResponse addSchoolGrades(@RequestParam String schoolCode, @RequestBody List<String> gradesName) {
        return schoolManagerService.addSchoolGrades(schoolCode, gradesName);
    }

    @PostMapping(REMOVE_SCHOOL_GRADES)
    public BasicResponse removeSchoolGrades(@RequestParam String schoolCode, @RequestBody List<String> gradesName) {
        return schoolManagerService.removeSchoolGrades(schoolCode, gradesName);
    }

    @PostMapping(ADD_CLASSES_TO_GRADE)
    public BasicResponse addClassesToGrade(
            @RequestParam String schoolCode,
            @RequestParam String gradeName,
            @RequestParam int classesCount) {
        return schoolManagerService.addClassesToGrade(schoolCode, gradeName, classesCount);

    }

    @PostMapping(ADD_ADDITIONAL_CLASS_TO_GRADE)
    public BasicResponse addAdditionalClassToGrade(
            @RequestParam String schoolCode,
            @RequestParam String gradeName,
            @RequestParam String className) {

        return schoolManagerService.addAdditionalClassToGrade(schoolCode, gradeName, className);

    }

    @PostMapping(ASSIGN_TEACHER_TO_CLASSES)
    public BasicResponse assignTeacherToClasses(
            @RequestParam String schoolCode,
            @RequestParam String teacherId,
            @RequestBody List<String> classNames) {
        return schoolManagerService.assignTeacherToClasses(schoolCode, teacherId, classNames);
    }

    @PostMapping(REMOVE_TEACHER_FROM_CLASS)
    public BasicResponse removeTeacherFromClass(@RequestParam String schoolCode,
                                                @RequestParam String teacherId,
                                                @RequestParam String className) {
        return schoolManagerService.removeTeacherFromClass(schoolCode, teacherId, className);
    }

    @GetMapping(GET_CLASS_NAMES_BY_SCHOOL_AND_GRADE)
    public BasicResponse getClassNamesBySchoolAndGrade(@RequestParam String schoolCode, @RequestParam String gradeName) {
        return schoolGradeService.getClassNamesBySchoolAndGrade(schoolCode, gradeName);
    }

    @GetMapping(GET_GRADES)
    public BasicResponse getGradesBySchoolCode(@RequestParam String schoolCode) {
        return schoolService.getGradesBySchoolCode(schoolCode);
    }

    @PostMapping(ADD_LESSON_TO_TEACHER)
    public BasicResponse addLessonToTeacher(@RequestBody LessonDTO dto, @RequestParam String teacherId) {
        return teacherService.addLessonToTeacher(dto, teacherId);

    }

    @PostMapping(ADD_TEACHING_SUBJECT_TO_TEACHER)
    public BasicResponse addTeachingSubjectToTeacher(@RequestParam String teacherId, @RequestBody List<String> subjects) {
        return teacherService.addTeachingSubjectToTeacher(teacherId, subjects);

    }

    @PostMapping(REMOVE_TEACHING_SUBJECT_FROM_TEACHER)
    public BasicResponse removeTeachingSubjectFromTeacher(@RequestParam String teacherId, @RequestParam String subjectToRemove) {
        return teacherService.removeTeachingSubjectFromTeacher(teacherId, subjectToRemove);
    }

    @PostMapping(ASSIGN_STUDENT_TO_CLASS)
    public BasicResponse assignStudentToClass(
            @RequestParam String schoolCode,
            @RequestParam String studentId,
            @RequestParam String className
    ) {
        return schoolManagerService.assignStudentToClass(schoolCode, studentId, className);
    }

    @PostMapping(CHANGE_STUDENT_CLASS)
    public BasicResponse changeStudentClass(
            @RequestParam String schoolCode,
            @RequestParam String studentId,
            @RequestParam String gradeName,
            @RequestParam String newClassName
    ) {
        return schoolManagerService.changeStudentClass(schoolCode, studentId, gradeName, newClassName);
    }
    @GetMapping(GET_ALL_TEACHERS)
    public BasicResponse getAllTeachers(@RequestParam String schoolCode) {
        return schoolManagerService.getAllTeachers(schoolCode);
    }

    @GetMapping(GET_ALL_STUDENTS)
    public BasicResponse getAllStudents(@RequestParam String schoolCode) {
        return schoolManagerService.getAllStudents(schoolCode);
    }

    @GetMapping(GET_TEACHER_TEACHING_SUBJECTS)
    public BasicResponse getTeacherTeachingSubjects(@RequestParam String teacherId) {
        return teacherService.getTeacherTeachingSubjects(teacherId);
    }

    @GetMapping(GET_SCHOOL)
    public BasicResponse getSchool(@RequestParam String schoolManagerId) {
        return schoolService.getSchoolDTO(schoolManagerId);
    }
}







