package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.UserProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserProgressRepository extends JpaRepository<UserProgressEntity, Long> {


//    @Query("SELECT up FROM UserProgressEntity up JOIN up.user u WHERE u.username = :userName")
//    UserProgressEntity findByUserName(@Param("userName") String userName);

}