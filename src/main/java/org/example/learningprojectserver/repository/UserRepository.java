package org.example.learningprojectserver.repository;


import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.projection.UserCredentialsProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    //    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.passwordHash = :password")
//    UserEntity findByUsernameAndPasswordHash(@Param("username") String username, @Param("password") String password);
    @Query("SELECT u FROM UserEntity u WHERE u NOT IN " +
            "(SELECT s.user FROM Session s WHERE s.lastActivity >= :lastWeek)")
    List<UserEntity> findUsersNotLoggedInLastWeek(@Param("lastWeek") Date lastWeek);

    @Query("SELECT u FROM UserEntity u WHERE u.username = :userName")
    UserEntity findByUserName(@Param("userName") String userName);

    @Query("SELECT u.phoneNumber FROM UserEntity u WHERE u.userId=:userId")
    String findPhoneNumberByUserId(@Param("userId") String userId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    UserEntity findByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.userId = :userId")
    UserEntity findUserByUserId(@Param("userId") String userId);

    @Query("SELECT u FROM UserEntity u WHERE u.userId IN :userIds")
    List<UserEntity> findUsersByUserIds(@Param("userIds") List<String> userIds);

    @Query("SELECT u FROM UserEntity u WHERE u.role = 'SCHOOLMANAGER'")
    List<UserEntity> getAllSchoolManagers();


    @EntityGraph(value = "User.withSessions", type = EntityGraph.EntityGraphType.FETCH)
    @Query("select u from UserEntity u where u.userId = :userId")
    UserEntity loadUserWithSessionsByUserId(@Param("userId") String userId);

    @Query("SELECT u.salt AS salt, u.passwordHash AS passwordHash, u.phoneNumber AS phoneNumber FROM UserEntity u WHERE u.userId = :userId")
    UserCredentialsProjection findBasicCredentialsByUserId(@Param("userId") String userId);

}