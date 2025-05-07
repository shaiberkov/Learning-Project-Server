package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.TeacherTestResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherTestResultRepository extends JpaRepository<TeacherTestResultEntity,Long> {


}
