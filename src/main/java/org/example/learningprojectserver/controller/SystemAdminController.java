package org.example.learningprojectserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.example.learningprojectserver.constants.ControllerConstants.SystemAdmin.*;

@RestController
@RequestMapping(SYSTEM_ADMIN_BASE_PATH)
@RequiredArgsConstructor
public class SystemAdminController {

    private final SystemAdminService systemAdminService;


    @PostMapping(ASSIGN_SCHOOL_MANAGER)
    public BasicResponse assignSchoolManager(@RequestParam String userId) {
        return systemAdminService.assignUserAsSchoolManager(userId);
    }

    @PostMapping(ADD_NEW_SCHOOL_TO_SYSTEM)
    public BasicResponse addNewSchoolToSystem(@RequestParam String schoolName, @RequestParam String schoolCode) {
        return systemAdminService.addNewSchoolToSystem(schoolName, schoolCode);
    }

    @PostMapping(ASSIGN_SCHOOL_MANAGER_TO_SCHOOL)
    public BasicResponse connectSchoolToManager(@RequestParam String userId, @RequestParam String schoolCode) {
        return systemAdminService.assignSchoolManagerToSchool(userId, schoolCode);

    }

    @PostMapping(REMOVE_SCHOOL_MANAGER_FROM_SCHOOL)
    public BasicResponse removeSchoolManagerFromSchool(@RequestParam String userId) {
        return systemAdminService.removeSchoolManagerFromSchool(userId);
    }

    @GetMapping(GET_ALL_SCHOOLS)
    public BasicResponse getAllSchools() {
        return systemAdminService.getAllSchoolsDTO();
    }
}
