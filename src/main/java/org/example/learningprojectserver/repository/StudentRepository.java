package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.StudentEntity;
import org.example.learningprojectserver.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

}
