package org.example.learningprojectserver.service;

import org.example.learningprojectserver.dto.LessonDTO;
import org.example.learningprojectserver.entities.ClassRoomEntity;
import org.example.learningprojectserver.entities.LessonEntity;
import org.example.learningprojectserver.entities.ScheduleEntity;
import org.example.learningprojectserver.mappers.LessonEntityToLessonDTOMapper;
import org.example.learningprojectserver.repository.ClassRoomRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;
    private final LessonEntityToLessonDTOMapper lessonEntityToLessonDTOMapper;

    @Autowired
    public ClassRoomService(ClassRoomRepository classRoomRepository, LessonEntityToLessonDTOMapper lessonEntityToLessonDTOMapper) {
        this.classRoomRepository = classRoomRepository;
        this.lessonEntityToLessonDTOMapper = lessonEntityToLessonDTOMapper;
    }

    public BasicResponse getAllLessonsForClassRoom(String schoolCode, String classRoomName) {
        ClassRoomEntity classRoom = classRoomRepository.findBySchoolCodeAndClassName(schoolCode, classRoomName);
        if (classRoom == null) {
            return new BasicResponse(false, "אין כיתה בשם זה");
        }

        ScheduleEntity schedule = classRoom.getSchedule();
        if (schedule == null) {
            return new BasicResponse(false, classRoomName+"לא קיימת מערכת שעות לכיתה ");
        }

        List<LessonEntity> lessons = schedule.getLessons();
        if (lessons == null || lessons.isEmpty()) {
            return new BasicResponse(false, "לא נימצא שיעורים לכיתה זו");
        }

        List<LessonDTO> lessonDTOS = lessons.stream()
                .map(lessonEntityToLessonDTOMapper).toList();

        BasicResponse basicResponse = new BasicResponse<>(true, null);
        basicResponse.setData(lessonDTOS);
        return basicResponse;

    }
}
