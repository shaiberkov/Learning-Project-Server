package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
    // ניתן להוסיף כאן שאילתות מותאמות אישית אם יש צורך
}