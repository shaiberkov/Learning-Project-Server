package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.notification.dto.SystemMessageDTO;
import org.example.learningprojectserver.entities.MessageEntity;
import org.springframework.stereotype.Component;

@Component
public class MessageEntityToMessageDTOMapper implements Mapper<MessageEntity, SystemMessageDTO> {
    @Override
    public SystemMessageDTO apply(MessageEntity messageEntity) {
        SystemMessageDTO dto = new SystemMessageDTO();
        dto.setTitle(messageEntity.getTitle());
        dto.setContent(messageEntity.getContent());
        dto.setSentAt(messageEntity.getSentAt());
        dto.setSenderName(messageEntity.getSender().getUsername());
        return dto;
    }
}