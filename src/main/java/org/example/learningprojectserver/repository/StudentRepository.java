package org.example.learningprojectserver.repository;

import jakarta.annotation.PostConstruct;
import org.example.learningprojectserver.entities.StudentEntity;
import org.example.learningprojectserver.entities.TestEntity;
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


}
