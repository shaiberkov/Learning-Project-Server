package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.SchoolGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolGradeRepository extends JpaRepository<SchoolGradeEntity,Long> {
}
