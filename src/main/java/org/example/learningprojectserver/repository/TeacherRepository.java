package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.dto.TeacherDTO;
import org.example.learningprojectserver.entities.TeacherEntity;
import org.example.learningprojectserver.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    @Query("SELECT t.teachingSchool.schoolCode FROM TeacherEntity t WHERE t.userId = :userId")
    String findSchoolCodeByUserId(@Param("userId") String userId);



    @Query(
            value = """
          SELECT
            u.username                             AS teacherName,
            u.email                                AS email,
            u.phone_number                         AS phone,
            GROUP_CONCAT(DISTINCT ts.subject_name) AS subjectsConcat,
            GROUP_CONCAT(DISTINCT cr1.name)        AS classesConcat,
            GROUP_CONCAT(DISTINCT g.grade_name)    AS gradesConcat
          FROM teacher_entity t
            JOIN user_entity u ON t.id = u.id
            JOIN school_entity sch ON t.school_id = sch.id AND sch.school_code = :schoolCode
            LEFT JOIN teacher_subject ts ON t.id = ts.teacher_id
            LEFT JOIN teacher_classroom tc ON t.id = tc.teacher_id
            LEFT JOIN class_room_entity cr1 ON tc.classroom_id = cr1.id
            LEFT JOIN lesson_entity l ON t.id = l.teacher_id
            LEFT JOIN schedule_entity s ON l.schedule_id = s.id
            LEFT JOIN class_room_entity cr2 ON s.class_room_id = cr2.id
            LEFT JOIN school_grade_entity g ON cr2.grade_id = g.id
          WHERE u.user_id = :userId
          GROUP BY u.id, u.username, u.email, u.phone_number
        """,
            nativeQuery = true
    )
    TeacherDTO findTeacherDTOByUserIdAndSchoolCode(
            @Param("userId") String userId,
            @Param("schoolCode") String schoolCode
    );

    @Query("""
        SELECT t 
        FROM TeacherEntity t
        JOIN FETCH t.teachingSubjects s 
        WHERE t.userId = :teacherId
    """)
    TeacherEntity findByUserIdWithSubjects(@Param("teacherId") String teacherId);
    @Query("SELECT t FROM TeacherEntity t WHERE t.userId = :userId")
    TeacherEntity findByUserId(@Param("userId") String userId);
    @Query("""
        SELECT DISTINCT t
        FROM TeacherEntity t
        LEFT JOIN FETCH t.tests test
        WHERE t.userId = :userId
    """)
   TeacherEntity findTeacherWithTests(@Param("userId") String userId);
}
