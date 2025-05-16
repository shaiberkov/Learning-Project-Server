package org.example.learningprojectserver.notification.dto;

import org.example.learningprojectserver.dto.MessageDTO;

public class SystemMessageDTO {
    private MessageDTO message;

    public SystemMessageDTO() {}

    public SystemMessageDTO(MessageDTO message) {
        this.message = message;
    }

    public MessageDTO getMessage() {
        return message;
    }

    public void setMessage(MessageDTO message) {
        this.message = message;
    }
}
