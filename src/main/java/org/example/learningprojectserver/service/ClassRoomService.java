package org.example.learningprojectserver.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.LessonDTO;
import org.example.learningprojectserver.entities.ClassRoomEntity;
import org.example.learningprojectserver.entities.LessonEntity;
import org.example.learningprojectserver.entities.ScheduleEntity;
import org.example.learningprojectserver.mappers.LessonEntityToLessonDTOMapper;
import org.example.learningprojectserver.mappers.LessonsToScheduleMapper;
import org.example.learningprojectserver.repository.ClassRoomRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;
    private final LessonsToScheduleMapper lessonsToScheduleMapper;

//    public BasicResponse getAllLessonsForClassRoom(String schoolCode, String classRoomName) {
//        ClassRoomEntity classRoom = classRoomRepository.findBySchoolCodeAndClassName(schoolCode, classRoomName);
//        if (classRoom == null) {
//            return new BasicResponse(false, "אין כיתה בשם זה");
//        }
//
//        ScheduleEntity schedule = classRoom.getSchedule();
//        if (schedule == null) {
//            return new BasicResponse(false, classRoomName+"לא קיימת מערכת שעות לכיתה ");
//        }
//
//        List<LessonEntity> lessons = schedule.getLessons();
//        if (lessons == null || lessons.isEmpty()) {
//            return new BasicResponse(false, "לא נימצא שיעורים לכיתה זו");
//        }
//
//        List<LessonDTO> lessonDTOS = lessons.stream()
//                .map(lessonEntityToLessonDTOMapper).toList();
//
//        BasicResponse basicResponse = new BasicResponse<>(true, null);
//        basicResponse.setData(lessonDTOS);
//        return basicResponse;
//
//    }


//    @PostConstruct
//    public void init() {
//        System.out.println(getAllLessonsForClassRoom("10","ג2"));
//    }

    @Cacheable(
            value = "classRoomSchedule",
            key = "#schoolCode + '_' + #classRoomName"
    )
    public BasicResponse getScheduleForClassRoom(String schoolCode, String classRoomName) {
        ClassRoomEntity classRoom = classRoomRepository.findBySchoolCodeAndClassName(schoolCode, classRoomName);
        if (classRoom == null) {
            return new BasicResponse(false, "אין כיתה בשם זה");
        }

        ScheduleEntity schedule = classRoom.getSchedule();
        if (schedule == null) {
            return new BasicResponse(false, classRoomName + " לא קיימת מערכת שעות לכיתה ");
        }

        List<LessonEntity> lessons = schedule.getLessons();
        if (lessons == null || lessons.isEmpty()) {
            return new BasicResponse(false, "לא נמצאו שיעורים לכיתה זו");
        }

        Map<DayOfWeek, List<LessonDTO>> lessonsByDay=lessonsToScheduleMapper.apply(lessons);
        BasicResponse response = new BasicResponse(true, null);
        response.setData(lessonsByDay);
        return response;
    }

}
