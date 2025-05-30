package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.dto.LessonDTO;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.example.learningprojectserver.constants.ControllerConstants.Lesson.LESSON_BASE_PATH;

@RestController
@RequestMapping(LESSON_BASE_PATH)
public class LessonController {


//    private final TeacherService teacherService;
//@Autowired
//    public LessonController(TeacherService teacherService) {
//        this.teacherService = teacherService;
//    }

//    @GetMapping("/teacher/lessons")
//    public BasicResponse getLessonsForTeacher(@RequestParam String teacherId) {
//    return teacherService.getLessonsForTeacher(teacherId);
   //}



}

