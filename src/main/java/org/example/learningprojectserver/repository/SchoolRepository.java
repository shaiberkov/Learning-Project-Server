package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.entities.SchoolEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {

    @Query("SELECT s FROM SchoolEntity s WHERE s.schoolCode = :schoolCode")
    SchoolEntity findBySchoolCode(@Param("schoolCode") String schoolCode);

    @Query("SELECT s FROM SchoolEntity s WHERE s.schoolManager.userId = :schoolManagerId")
    SchoolEntity findBySchoolManagerId(@Param("schoolManagerId") String schoolManagerId);

    @Query("SELECT t FROM SchoolEntity s JOIN s.teachers t WHERE s.schoolManager.userId = :userId")
    List<UserEntity> findTeachersByUserId(@Param("userId") String userId);

    @Query("SELECT st FROM SchoolEntity s JOIN s.students st WHERE s.schoolManager.userId = :userId")
    List<UserEntity> findStudentsByUserId(@Param("userId") String userId);

    @Query("""
    SELECT st FROM SchoolEntity s 
    JOIN s.classRooms c 
    JOIN c.students st 
    WHERE s.schoolManager.userId = :userId AND c.name = :className
""")
    List<UserEntity> findClassRoomStudentsByUserIdAndClassName(@Param("userId") String userId, @Param("className") String className);

    @Query("""
    SELECT st FROM SchoolEntity s
    JOIN s.schoolGrades g
    JOIN g.classes c
    JOIN c.students st
    WHERE s.schoolManager.userId = :userId AND g.gradeName = :gradeName
""")
    List<UserEntity> findStudentsByUserIdAndGradeName(@Param("userId") String userId, @Param("gradeName") String gradeName);

//    @Query("SELECT s.schoolManager FROM SchoolEntity s WHERE s.schoolManager.id = :userId")
//    UserEntity findSchoolManagerByUserId(@Param("userId") String userId);

    @Query("""
    SELECT t FROM SchoolEntity s
    JOIN s.schoolGrades g
    JOIN g.classes c
    JOIN c.teachers t
    WHERE s.schoolManager.userId = :userId AND g.gradeName = :gradeName
""")
    List<UserEntity> findTeachersByUserIdAndGradeName(@Param("userId") String userId, @Param("gradeName") String gradeName);


}
