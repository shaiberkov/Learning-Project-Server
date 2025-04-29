package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.SystemAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemAdminRepository extends JpaRepository<SystemAdminEntity,Long> {
}
