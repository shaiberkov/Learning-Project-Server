package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

}
