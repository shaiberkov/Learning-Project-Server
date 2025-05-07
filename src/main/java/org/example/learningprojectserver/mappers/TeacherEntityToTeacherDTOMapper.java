package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.QuestionDTO;
import org.example.learningprojectserver.dto.TeacherDTO;
import org.example.learningprojectserver.entities.ClassRoomEntity;
import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.entities.TeacherEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class TeacherEntityToTeacherDTOMapper implements Mapper<TeacherEntity, TeacherDTO>{


    @Override
    public TeacherDTO apply(TeacherEntity teacher) {
        TeacherDTO dto = new TeacherDTO();
        dto.setTeacherName(teacher.getUsername());
        dto.setEmail(teacher.getEmail());
        dto.setPhone(teacher.getPhoneNumber());
        dto.setSubjects(teacher.getTeachingSubjects());
        dto.setClasses(teacher.getTeachingClassRooms().stream().map(ClassRoomEntity::getName).toList());
        dto.setGrades(
                teacher.getLessons().stream()
                        .map(lesson -> lesson.getSchedule().getClassRoom().getGrade().getGradeName())
                        .distinct()
                        .toList()
        );
        return dto;


    }


}
