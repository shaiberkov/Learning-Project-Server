package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.TeacherEntity;
import org.example.learningprojectserver.entities.TeacherTestEntity;
import org.example.learningprojectserver.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherTestRepository extends JpaRepository<TeacherTestEntity, Long> {
    @Query("SELECT t FROM TeacherTestEntity t WHERE t.id = :testId")
    TeacherTestEntity findTeacherTestByTestId(@Param("testId") Long testId);
}
