package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Learning-App/System-Admin")
public class SystemAdminController {

    private final SystemAdminService systemAdminService;

    @Autowired
    public SystemAdminController(SystemAdminService systemAdminService) {
        this.systemAdminService = systemAdminService;
    }

    @PostMapping("/assign-school-manager")
    public BasicResponse assignSchoolManager(@RequestParam String userId) {
        return systemAdminService.assignUserAsSchoolManager(userId);
    }

    @PostMapping("/add-new-school-to-system")
    public BasicResponse addNewSchoolToSystem(@RequestParam String schoolName, @RequestParam String schoolCode) {
        return systemAdminService.addNewSchoolToSystem(schoolName, schoolCode);
    }

    @PostMapping("/assign-school-manager-to-school")
    public BasicResponse connectSchoolToManager(@RequestParam String userId, @RequestParam String schoolCode) {
        return systemAdminService.assignSchoolManagerToSchool(userId, schoolCode);

    }

    @PostMapping("/remove-school-manager-from-school")
    public BasicResponse removeSchoolManagerFromSchool(@RequestParam String userId) {
        return systemAdminService.removeSchoolManagerFromSchool(userId);
    }
}
