package org.example.learningprojectserver.controller;


import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.service.UpcomingEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Learning-App/UpcomingEvents")
public class UpcomingEventController {

    private final UpcomingEventService upcomingEventService;


    public UpcomingEventController(UpcomingEventService upcomingEventService) {
        this.upcomingEventService = upcomingEventService;
    }


    @GetMapping("/upcoming-events")
    public List<UpcomingEventDto> getUpcomingEvents(
            @RequestParam Role role,
            @RequestParam String userId) {

        List<UpcomingEventDto> events = upcomingEventService.getUpcomingEvents(role, userId);
        return events;
    }

}
