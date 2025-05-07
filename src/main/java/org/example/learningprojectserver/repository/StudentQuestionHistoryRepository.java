package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.StudentQuestionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentQuestionHistoryRepository extends JpaRepository<StudentQuestionHistoryEntity, Long> {

//    @Query("SELECT qh FROM QuestionHistoryEntity qh JOIN qh.user u WHERE u.username = :userName")
//    QuestionHistoryEntity findByUserName(@Param("userName") String userName);

    @Query("SELECT sqh FROM StudentQuestionHistoryEntity sqh WHERE sqh.student.userId = :studentId")
    StudentQuestionHistoryEntity findStudentQuestionHistoryByUserId(@Param("studentId") String studentId);
}