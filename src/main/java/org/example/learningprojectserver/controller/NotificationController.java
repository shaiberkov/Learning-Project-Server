package org.example.learningprojectserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.example.learningprojectserver.constants.ControllerConstants.Notification.CONNECT;
import static org.example.learningprojectserver.constants.ControllerConstants.Notification.NOTIFICATION_BASE_PATH;

@RestController
@RequestMapping(NOTIFICATION_BASE_PATH)
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(CONNECT)
    public SseEmitter connect(@RequestParam String userId) {
        return notificationService.connect(userId);
    }
}
