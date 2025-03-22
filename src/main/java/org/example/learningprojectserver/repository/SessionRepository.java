package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT DISTINCT DATE(s.lastActivity) FROM Session s WHERE s.user.username = :username ORDER BY DATE(s.lastActivity) DESC")
    List<Date> findDistinctActiveDays(@Param("username") String username);

}
