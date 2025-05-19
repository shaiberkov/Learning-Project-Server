package org.example.learningprojectserver.notification.event;

import org.example.learningprojectserver.notification.dto.NotificationDTO;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;


public class NotificationEvent<T> extends ApplicationEvent {

    private final Collection<String> recipients;
    private final NotificationDTO<T> notificationDTO;

    public NotificationEvent(Collection<String> recipients, NotificationDTO<T> notificationDTO) {
        super(notificationDTO);
        this.recipients = recipients;
        this.notificationDTO = notificationDTO;
    }

    public Collection<String> getRecipients() {
        return recipients;
    }

    public NotificationDTO<T> getNotificationDTO() {
        return notificationDTO;
    }
}
