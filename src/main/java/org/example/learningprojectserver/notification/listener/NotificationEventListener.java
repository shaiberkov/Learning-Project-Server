package org.example.learningprojectserver.notification.listener;

import org.example.learningprojectserver.notification.event.NotificationEvent;
import org.example.learningprojectserver.notification.dto.NotificationDTO;
import org.example.learningprojectserver.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;



@Component
public class NotificationEventListener {

    private final NotificationService notificationService;
@Autowired
    public NotificationEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Async
    @EventListener
    public <T> void handle(NotificationEvent<T> event) {
        NotificationDTO<T> dto = event.getNotificationDTO();
        for (String userId : event.getRecipients()) {
            notificationService.sendNotification(userId, dto);
        }
    }
}
