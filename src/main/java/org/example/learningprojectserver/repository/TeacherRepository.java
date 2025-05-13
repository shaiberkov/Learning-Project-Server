package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.TeacherEntity;
import org.example.learningprojectserver.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    @Query("SELECT t.teachingSchool.schoolCode FROM TeacherEntity t WHERE t.userId = :userId")
    String findSchoolCodeByUserId(@Param("userId") String userId);
}
