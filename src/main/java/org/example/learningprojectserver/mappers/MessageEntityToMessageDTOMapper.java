package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.MessageDTO;
import org.example.learningprojectserver.entities.MessageEntity;
import org.springframework.stereotype.Component;

@Component
public class MessageEntityToMessageDTOMapper implements Mapper<MessageEntity, MessageDTO> {
    @Override
    public MessageDTO apply(MessageEntity messageEntity) {
        MessageDTO dto = new MessageDTO();
        dto.setTitle(messageEntity.getTitle());
        dto.setContent(messageEntity.getContent());
        dto.setSentAt(messageEntity.getSentAt());
        dto.setSenderName(messageEntity.getSender().getUsername());
        return dto;
    }
}