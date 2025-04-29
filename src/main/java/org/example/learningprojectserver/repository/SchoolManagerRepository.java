package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.SchoolManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolManagerRepository extends JpaRepository<SchoolManagerEntity,Long> {
}
