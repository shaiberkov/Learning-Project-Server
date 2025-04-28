package org.example.learningprojectserver.repository;


import org.example.learningprojectserver.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {


//    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.passwordHash = :password")
//    UserEntity findByUsernameAndPasswordHash(@Param("username") String username, @Param("password") String password);
@Query("SELECT u FROM UserEntity u WHERE u NOT IN " +
        "(SELECT s.user FROM Session s WHERE s.lastActivity >= :lastWeek)")
List<UserEntity> findUsersNotLoggedInLastWeek(@Param("lastWeek") Date lastWeek);

    @Query("SELECT u FROM UserEntity u WHERE u.username = :userName")
    UserEntity findByUserName(@Param("userName") String userName);

@Query("SELECT u.phoneNumber FROM UserEntity u WHERE u.username=:userName")
String findPhoneNumberByUsername(@Param ("userName")String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    UserEntity findByUsername(String username);

}