package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.MessageEntity;
import org.example.learningprojectserver.entities.PracticeTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeTestRepository extends JpaRepository<PracticeTestEntity, Long> {

}
