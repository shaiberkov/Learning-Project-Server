package org.example.learningprojectserver.controller;


import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.service.UpcomingEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.learningprojectserver.constants.ControllerConstants.UpcomingEvents.GET_UPCOMING_EVENTS;
import static org.example.learningprojectserver.constants.ControllerConstants.UpcomingEvents.UPCOMING_EVENTS_BASE_PATH;

@RestController
@RequestMapping(UPCOMING_EVENTS_BASE_PATH)
@RequiredArgsConstructor
public class UpcomingEventController {

    private final UpcomingEventService upcomingEventService;




    @GetMapping(GET_UPCOMING_EVENTS)
    public List<UpcomingEventDto> getUpcomingEvents(
            @RequestParam Role role,
            @RequestParam String userId) {

        List<UpcomingEventDto> events = upcomingEventService.getUpcomingEvents(role, userId);
        return events;
    }

}
