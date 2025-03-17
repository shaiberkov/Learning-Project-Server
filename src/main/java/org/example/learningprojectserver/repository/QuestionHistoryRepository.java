package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.QuestionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionHistoryRepository extends JpaRepository<QuestionHistoryEntity, Long> {

    @Query("SELECT qh FROM QuestionHistoryEntity qh JOIN qh.user u WHERE u.username = :userName")
    QuestionHistoryEntity findByUserName(@Param("userName") String userName);

}