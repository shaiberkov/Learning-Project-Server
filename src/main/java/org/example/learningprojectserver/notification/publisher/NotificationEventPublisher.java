package org.example.learningprojectserver.notification.publisher;

import org.example.learningprojectserver.notification.dto.NotificationDTO;
import org.example.learningprojectserver.notification.event.NotificationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class NotificationEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public NotificationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public <T> void publish(Collection<String> recipients, NotificationDTO<T> notificationDTO) {
        NotificationEvent<T> event = new NotificationEvent<>(recipients, notificationDTO);
        applicationEventPublisher.publishEvent(event);
    }
}
