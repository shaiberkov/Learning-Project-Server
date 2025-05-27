package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.entities.ClassRoomEntity;
import org.example.learningprojectserver.entities.StudentEntity;
import org.example.learningprojectserver.entities.TeacherEntity;
import org.example.learningprojectserver.entities.TeacherTestEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.example.learningprojectserver.utils.HolidayUtils.daysUntil;
import static org.example.learningprojectserver.utils.HolidayUtils.fetchHebrewHolidayEvents;
@Component
public class TeacherEntityToUpcomingEventDTOMapper implements Mapper<TeacherEntity, List<UpcomingEventDto>>{
    @Override
    public List<UpcomingEventDto> apply(TeacherEntity teacher) {


        List<UpcomingEventDto> upcomingEventDtos = new ArrayList<>();
        List<TeacherTestEntity> teacherTestEntities=teacher.getTests();

        for (TeacherTestEntity testEntity: teacherTestEntities) {
            int eventInDays = daysUntil(testEntity.getStartTime());
            List<String> classRooms = testEntity.getStudents()
                    .stream()
                    .map(StudentEntity::getClassRoom)
                    .map(ClassRoomEntity::getName)
                    .distinct()
                    .toList();

            String subject = testEntity.getSubject();
            String topic = testEntity.getTopic();

            String subjectText = "במקצוע " + subject + " בנושא " + topic;

            if (eventInDays >= 0) {
                String eventName;
                if (classRooms.size() == 1) {
                    eventName = "מבחן " + subjectText + " לתלמידים בכיתה " + classRooms.get(0);
                } else if (classRooms.size() > 1) {
                    String allButLast = String.join(", ", classRooms.subList(0, classRooms.size() - 1));
                    String last = classRooms.get(classRooms.size() - 1);
                    eventName = "מבחן " + subjectText + " לתלמידים בכיתות " + allButLast + " ו-" + last;
                } else {
                    eventName = "מבחן " + subjectText + " לתלמידים"; // במקרה שאין כיתות בכלל
                }
                upcomingEventDtos.add(new UpcomingEventDto(eventName, eventInDays));
            }

        }
        upcomingEventDtos.addAll(fetchHebrewHolidayEvents());

        upcomingEventDtos.sort(Comparator.comparingInt(UpcomingEventDto::getEventInDays));


        return upcomingEventDtos;
    }
}
