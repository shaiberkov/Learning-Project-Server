package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.dto.LessonDTO;
import org.example.learningprojectserver.entities.ClassRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoomEntity, Long> {

    @Query("SELECT c FROM ClassRoomEntity c WHERE c.school.schoolCode = :schoolCode AND c.name = :className")
    ClassRoomEntity findBySchoolCodeAndClassName(@Param("schoolCode") String schoolCode, @Param("className") String className);

    @Query("SELECT c FROM ClassRoomEntity c WHERE c.school.schoolCode = :schoolCode AND c.grade.gradeName = :gradeName")
    List<ClassRoomEntity> findClassRoomsBySchoolCodeAndGradeName(@Param("schoolCode") String schoolCode, @Param("gradeName") String gradeName);

    @Query("SELECT c FROM ClassRoomEntity c WHERE c.name = :name")
    ClassRoomEntity findClassRoomByName(@Param("name") String name);

    @Query("SELECT s.classRoom FROM StudentEntity s WHERE s.userId = :userId")
    ClassRoomEntity findClassRoomOfUserByUserId(@Param("userId") String userId);

    @Query("SELECT c FROM ClassRoomEntity c WHERE c.school.schoolCode = :schoolCode AND c.name IN :classNames")
    List<ClassRoomEntity> findBySchoolCodeAndClassNames(@Param("schoolCode") String schoolCode, @Param("classNames") List<String> classNames);
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
    WHERE c.school.schoolCode = :schoolCode AND c.name = :classRoomName
""")
    List<LessonDTO> findLessonsBySchoolCodeAndClassRoomName(@Param("schoolCode") String schoolCode,
                                                            @Param("classRoomName") String classRoomName);
    @Query("""
    SELECT c.name
    FROM ClassRoomEntity c
    WHERE c.school.schoolCode = :schoolCode AND c.grade.gradeName = :gradeName
""")
    List<String> findClassRoomNamesBySchoolCodeAndGradeName(@Param("schoolCode") String schoolCode,
                                                            @Param("gradeName") String gradeName);
    @Query("""
    SELECT DISTINCT c.name
    FROM ClassRoomEntity c
    WHERE c.school.schoolCode = :schoolCode
""")
    List<String> findDistinctClassNamesBySchoolCode(@Param("schoolCode") String schoolCode);

}



