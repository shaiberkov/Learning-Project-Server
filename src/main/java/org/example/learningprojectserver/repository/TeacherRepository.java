package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.TeacherEntity;
import org.example.learningprojectserver.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
}
