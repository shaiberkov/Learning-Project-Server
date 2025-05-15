package org.example.learningprojectserver.notification.dto;

import org.example.learningprojectserver.notification.NotificationType;

public class NotificationDTO<T> {
    private NotificationType type;
    private T payload;

    public NotificationDTO() {}  // דרוש ל-Jackson

    public NotificationDTO(NotificationType type, T payload) {
        this.type = type;
        this.payload = payload;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
