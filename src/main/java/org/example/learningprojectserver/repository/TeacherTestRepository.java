package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.TeacherEntity;
import org.example.learningprojectserver.entities.TeacherTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherTestRepository extends JpaRepository<TeacherTestEntity, Long> {

}
