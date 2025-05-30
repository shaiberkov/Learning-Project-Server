package org.example.learningprojectserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.learningprojectserver.constants.ControllerConstants.Schedule.SCHEDULE_BASE_PATH;

@RestController
@RequestMapping(SCHEDULE_BASE_PATH)
public class ScheduleController {
}
