package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.MessageEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
//    @Query("SELECT m FROM MessageEntity m WHERE m.sender.userId = :userId OR m.receiver.userId = :userId")
//    List<MessageEntity> findMessagesByUserId(@Param("userId") String userId);
}
