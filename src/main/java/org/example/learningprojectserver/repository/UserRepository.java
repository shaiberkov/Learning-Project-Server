package org.example.learningprojectserver.repository;


import org.example.learningprojectserver.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {


    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.passwordHash = :password")
    UserEntity findByUsernameAndPasswordHash(@Param("username") String username, @Param("password") String password);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    UserEntity findByUsername(String username);
}