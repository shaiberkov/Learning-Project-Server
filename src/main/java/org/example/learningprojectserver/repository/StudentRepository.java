package org.example.learningprojectserver.repository;

import jakarta.annotation.PostConstruct;
import org.example.learningprojectserver.dto.StudentTestStatusDTO;
import org.example.learningprojectserver.entities.StudentEntity;
import org.example.learningprojectserver.entities.TestEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    @Query("SELECT s FROM StudentEntity s WHERE s.userId = :userId")
    StudentEntity findStudentByStudentId( @Param("userId") String id);

    @Query("SELECT s FROM StudentEntity s WHERE s.userId IN :userIds")
    List<StudentEntity> findStudentsByUserIds(@Param("userIds") List<String> userIds);

    @Query("SELECT s.schoolName.schoolCode FROM StudentEntity s WHERE s.userId = :userId")
    String findSchoolCodeByUserId(@Param("userId") String userId);


    @Query("""
    SELECT DISTINCT s FROM StudentEntity s
    LEFT JOIN FETCH s.classRoom
    LEFT JOIN FETCH s.studentProgressEntity p
    LEFT JOIN FETCH s.studentQuestionHistoryEntity h
    LEFT JOIN FETCH p.subTopicSuccessStreak
    LEFT JOIN FETCH p.subTopicIncorrectStreak
    LEFT JOIN FETCH p.skillLevelsBySubTopic
    WHERE s.userId = :userId
""")
    StudentEntity findStudentWithAllData(@Param("userId") String userId);
    @Query("""
    SELECT s FROM StudentEntity s
    LEFT JOIN FETCH s.classRoom c
    LEFT JOIN FETCH s.studentProgressEntity p
    LEFT JOIN FETCH p.skillLevelsBySubTopic m
    LEFT JOIN FETCH s.studentQuestionHistoryEntity h
    WHERE s.userId = :userId
""")
    StudentEntity findStudentWithProgressAndHistory(@Param("userId") String userId);


    @Query("""
    SELECT new org.example.learningprojectserver.dto.StudentTestStatusDTO(
        t.id,
        t.subject,
        t.topic,
        COALESCE(r.score, -1),
        t.startTime,
        t.timeLimitMinutes
    )
    FROM StudentEntity s
    JOIN s.teacherTests t
    LEFT JOIN TeacherTestResultEntity r ON r.test.id = t.id AND r.student.userId = :userId
    WHERE s.userId = :userId
""")
    List<StudentTestStatusDTO> findAllTestsForStudent(@Param("userId") String userId);

    @Query("""
        SELECT new org.example.learningprojectserver.dto.StudentTestStatusDTO(
            t.id,
            t.subject,
            t.topic,
            r.score,
            t.startTime,
            t.timeLimitMinutes
        )
        FROM TeacherTestResultEntity r
        JOIN r.test t
        WHERE r.student.userId = :userId
    """)
    List<StudentTestStatusDTO> findTestStatusForStudent(@Param("userId") String userId);
    @Query("""
    SELECT s FROM StudentEntity s
    JOIN FETCH s.classRoom
    WHERE s.userId = :userId
""")
    StudentEntity findStudentWithClassRoomByUserId(@Param("userId") String userId);


}


