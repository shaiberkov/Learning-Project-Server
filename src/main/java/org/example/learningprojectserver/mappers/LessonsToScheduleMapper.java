package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.LessonDTO;
import org.example.learningprojectserver.entities.LessonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
@Component
public class LessonsToScheduleMapper implements Mapper<List<LessonEntity>,Map<DayOfWeek, List<LessonDTO>> >{

    private final LessonEntityToLessonDTOMapper lessonEntityToLessonDTOMapper;
@Autowired
    public LessonsToScheduleMapper(LessonEntityToLessonDTOMapper lessonEntityToLessonDTOMapper) {
        this.lessonEntityToLessonDTOMapper = lessonEntityToLessonDTOMapper;
    }

    @Override
    public Map<DayOfWeek, List<LessonDTO>> apply(List<LessonEntity> lessons) {
     return null;

    }

}