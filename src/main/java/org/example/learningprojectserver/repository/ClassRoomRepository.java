package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.ClassRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoomEntity, Long> {

    @Query("SELECT c FROM ClassRoomEntity c WHERE c.school.schoolCode = :schoolCode AND c.name = :className")
    ClassRoomEntity findBySchoolCodeAndClassName(@Param("schoolCode") String schoolCode, @Param("className") String className);

    @Query("SELECT c FROM ClassRoomEntity c WHERE c.school.schoolCode = :schoolCode AND c.grade.gradeName = :gradeName")
    List<ClassRoomEntity> findClassRoomsBySchoolCodeAndGradeName(@Param("schoolCode") String schoolCode, @Param("gradeName") String gradeName);

    @Query("SELECT c FROM ClassRoomEntity c WHERE c.name = :name")
    ClassRoomEntity findClassRoomByName(@Param("name") String name);

    @Query("SELECT s.classRoom FROM StudentEntity s WHERE s.userId = :userId")
    ClassRoomEntity findClassRoomOfUserByUserId(@Param("userId") String userId);}
