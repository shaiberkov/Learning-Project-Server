package org.example.learningprojectserver.strategy.message;

import org.example.learningprojectserver.entities.UserEntity;

import java.util.List;

public interface MessageRecipientStrategy {
    List<UserEntity> getRecipients(String senderId);
}