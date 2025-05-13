package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.LessonDTO;
import org.example.learningprojectserver.entities.LessonEntity;
import org.springframework.stereotype.Component;

@Component
public class LessonEntityToLessonDTOMapper implements Mapper<LessonEntity, LessonDTO> {

    @Override
    public LessonDTO apply(LessonEntity lesson) {
        LessonDTO dto = new LessonDTO();
        dto.setSubject( lesson.getSubject());
        dto.setDayOfWeek( lesson.getDayOfWeek());
        dto.setStartTime(lesson.getStartTime().toString());
        dto.setEndTime(lesson.getEndTime().toString());
        dto.setClassRoomName(lesson.getSchedule().getClassRoom().getName());
        dto.setTeacherName(lesson.getTeacher().getUsername());

        return dto;
    }
}
