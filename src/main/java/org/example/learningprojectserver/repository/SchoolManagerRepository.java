package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.SchoolManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolManagerRepository extends JpaRepository<SchoolManagerEntity,Long> {

    @Query("SELECT s.school.schoolCode FROM SchoolManagerEntity s WHERE s.userId = :userId")
    String findSchoolCodeByUserId(@Param("userId") String userId);
}
