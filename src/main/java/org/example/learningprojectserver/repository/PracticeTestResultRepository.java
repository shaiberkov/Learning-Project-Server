package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.PracticeTestEntity;
import org.example.learningprojectserver.entities.PracticeTestResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeTestResultRepository extends JpaRepository<PracticeTestResultEntity, Long> {
}
