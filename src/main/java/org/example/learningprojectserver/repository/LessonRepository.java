package org.example.learningprojectserver.repository;

import org.example.learningprojectserver.entities.LessonEntity;
import org.example.learningprojectserver.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long>  {

    @Query("SELECT l FROM LessonEntity l WHERE l.teacher.userId = :teacherId")
    List<LessonEntity> findLessonsByTeacherId(String teacherId);
}
