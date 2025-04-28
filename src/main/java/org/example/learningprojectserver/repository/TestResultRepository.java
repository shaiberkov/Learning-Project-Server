package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.TestResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultRepository extends JpaRepository<TestResultEntity, Long> {
}