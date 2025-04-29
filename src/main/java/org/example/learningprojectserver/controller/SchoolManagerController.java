package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.dto.UserDto;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.SchoolManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Learning-App/School-Manager")
public class SchoolManagerController {


    private final SchoolManagerService schoolManagerService;

    @Autowired
    public SchoolManagerController(SchoolManagerService schoolManagerService) {
        this.schoolManagerService = schoolManagerService;
    }



    @PostMapping("/assign-user-as-school-teacher")
    public BasicResponse assignUserAsSchoolTeacher(@RequestParam String userId, @RequestParam String schoolCode) {
        return schoolManagerService.assignUserAsSchoolTeacher(userId,schoolCode);
    }

    @PostMapping("/remove-teacher-from-school")
    public BasicResponse removeTeacherFromSchool(@RequestParam String userId) {
        return schoolManagerService.removeTeacherFromSchool(userId);
    }
}
