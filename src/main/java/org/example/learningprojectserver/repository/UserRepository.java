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


    @Query("SELECT u FROM UserEntity u WHERE u NOT IN " +
            "(SELECT s.user FROM Session s WHERE s.lastActivity >= :lastWeek)")
    List<UserEntity> findUsersNotLoggedInLastWeek(@Param("lastWeek") Date lastWeek);



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



    @Query("SELECT u.salt AS salt, u.passwordHash AS passwordHash, u.phoneNumber AS phoneNumber FROM UserEntity u WHERE u.userId = :userId")
    UserCredentialsProjection findBasicCredentialsByUserId(@Param("userId") String userId);

//    @Query("SELECT u.id FROM UserEntity u WHERE u.userId = :userId")
//    Long findIdByUserId(@Param("userId") String userId);

    @Query(
            value = """
          SELECT u.id
          FROM user_entity u
          WHERE u.user_id = :userId
        """,
            nativeQuery = true
    )
    Long findIdByUserId(@Param("userId") String userId);
}