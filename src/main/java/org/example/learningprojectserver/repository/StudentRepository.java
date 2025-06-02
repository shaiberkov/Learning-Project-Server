package org.example.learningprojectserver.repository;

import jakarta.annotation.PostConstruct;
import org.example.learningprojectserver.entities.StudentEntity;
import org.example.learningprojectserver.entities.TestEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    @Query("SELECT s FROM StudentEntity s WHERE s.userId IN :userIds")
    List<StudentEntity> findStudentsByUserIds(@Param("userIds") List<String> userIds);

    @Query("SELECT s.schoolName.schoolCode FROM StudentEntity s WHERE s.userId = :userId")
    String findSchoolCodeByUserId(@Param("userId") String userId);

//    @EntityGraph(attributePaths = {
//            "studentProgressEntity.skillLevelsBySubTopic",
//            "studentQuestionHistoryEntity"
//    })
//    @Query("""
//    select s
//    from StudentEntity s
//    left join fetch s.studentProgressEntity p
//    left join fetch p.skillLevelsBySubTopic
//    left join fetch s.studentQuestionHistoryEntity
//    where s.userId = :userId
//""")
//    StudentEntity findFullStudent(@Param("userId") String userId);

    @Query("""
        SELECT s FROM StudentEntity s
        LEFT JOIN FETCH s.studentProgressEntity
        LEFT JOIN FETCH s.studentQuestionHistoryEntity
        WHERE s.userId = :userId
    """)
    StudentEntity findStudentWithProgressAndHistory(@Param("userId") String userId);
}


