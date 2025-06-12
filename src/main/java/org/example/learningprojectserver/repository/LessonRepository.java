package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.dto.LessonDTO;
import org.example.learningprojectserver.entities.LessonEntity;
import org.example.learningprojectserver.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long>  {


    @Query("""
    SELECT new org.example.learningprojectserver.dto.LessonDTO(
        l.subject,
        l.dayOfWeek,
        l.startTime,
        l.endTime,
        c.name,
        t.username
    )
    FROM LessonEntity l
    JOIN l.schedule s
    JOIN s.classRoom c
    JOIN l.teacher t
    JOIN c.students student
    WHERE student.userId = :userId
""")
    List<LessonDTO> findLessonsByStudentId(@Param("userId") String userId);

    @Query("""
    SELECT new org.example.learningprojectserver.dto.LessonDTO(
        l.subject,
        l.dayOfWeek,
        l.startTime,
        l.endTime,
        c.name,
        t.username
    )
    FROM LessonEntity l
    JOIN l.schedule s
    JOIN s.classRoom c
    JOIN l.teacher t
    WHERE t.userId = :userId
""")
    List<LessonDTO> findLessonsByTeacherId(@Param("userId") String userId);

}
