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


    @Cacheable(
            value = "classRoomSchedule",
            key = "#schoolCode + '_' + #classRoomName"
    )
    public BasicResponse getScheduleForClassRoom(String schoolCode, String classRoomName) {

List<LessonDTO>lessons=classRoomRepository.findLessonsBySchoolCodeAndClassRoomName(schoolCode, classRoomName);
        Map<DayOfWeek, List<LessonDTO>> lessonsByDay = lessons.stream()
                .collect(Collectors.groupingBy(
                        LessonDTO::getDayOfWeek,
                        () -> new TreeMap<>(Comparator.comparingInt(day -> day == DayOfWeek.SUNDAY ? 0 : day.getValue())),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(LessonDTO::getStartTime))
                                        .collect(Collectors.toList())
                        )
                ));
        BasicResponse response = new BasicResponse(true, null);
        response.setData(lessonsByDay);
        return response;
    }

}
