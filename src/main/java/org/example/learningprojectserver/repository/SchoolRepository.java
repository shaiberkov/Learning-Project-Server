package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.entities.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {

    @Query("SELECT s FROM SchoolEntity s WHERE s.schoolCode = :schoolCode")
    SchoolEntity findBySchoolCode(@Param("schoolCode") String schoolCode);

}
