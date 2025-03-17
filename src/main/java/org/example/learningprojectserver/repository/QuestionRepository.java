package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    @Query("SELECT q FROM QuestionEntity q WHERE q.subject = :subject AND q.topic = :topic AND q.subTopic = :subTopic AND q.questionText = :questionText")
    QuestionEntity findBySubjectTopicSubTopicAndQuestionText(@Param("subject") String subject,
                                                                   @Param("topic") String topic,
                                                                   @Param("subTopic") String subTopic,
                                                                   @Param("questionText") String questionText);
    List<QuestionEntity> findAll();

    @Query("SELECT q FROM QuestionEntity q WHERE q.id = :id")
    QuestionEntity findQuestionById(@Param("id") Long id);

}