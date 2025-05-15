package org.example.learningprojectserver.notification.dto;

import java.time.LocalDateTime;

public class SystemMessageDTO {

    private String title;
    private String content;
     private LocalDateTime sentAt;
    private String senderName;

    public SystemMessageDTO(String title, String senderName, LocalDateTime sentAt, String content) {
        this.title = title;
        this.senderName = senderName;
        this.sentAt = sentAt;
        this.content = content;
    }
    public SystemMessageDTO() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
