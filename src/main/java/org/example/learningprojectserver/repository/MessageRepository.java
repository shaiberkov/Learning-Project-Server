package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.dto.MessageDTO;
import org.example.learningprojectserver.entities.MessageEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    @Query("""
 SELECT new org.example.learningprojectserver.dto.MessageDTO(
 m.title,
 m.content,
 m.sentAt,
 m.sender.username
)
FROM MessageEntity m
WHERE m.receiver.userId = :userId
""")
    List<MessageDTO> findMessagesByRecipientUserId(@Param("userId") String userId);

}
