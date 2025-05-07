package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.StudentProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentProgressRepository extends JpaRepository<StudentProgressEntity, Long> {


//    @Query("SELECT up FROM UserProgressEntity up JOIN up.user u WHERE u.username = :userName")
//    UserProgressEntity findByUserName(@Param("userName") String userName);
@Query("SELECT sp FROM StudentProgressEntity sp WHERE sp.student.userId = :studentId")
StudentProgressEntity findStudentProgressByUserId(@Param("studentId") String studentId);
}