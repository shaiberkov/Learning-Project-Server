package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.StudentTestStatusDTO;
import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.entities.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.example.learningprojectserver.utils.HolidayUtils.daysUntil;
import static org.example.learningprojectserver.utils.HolidayUtils.fetchHebrewHolidayEvents;

@Component
public class StudentEntityToUpcomingEventDTOMapper implements Mapper<StudentEntity, List<UpcomingEventDto>>{

    private final StudentEntityToStudentTestStatusDTOMapper studentEntityToStudentTestStatusDTOMapper;
@Autowired
    public StudentEntityToUpcomingEventDTOMapper(StudentEntityToStudentTestStatusDTOMapper studentEntityToStudentTestStatusDTOMapper) {
        this.studentEntityToStudentTestStatusDTOMapper = studentEntityToStudentTestStatusDTOMapper;
    }

    @Override
    public List<UpcomingEventDto> apply(StudentEntity student) {
        List<StudentTestStatusDTO> studentTestStatusDTOS = studentEntityToStudentTestStatusDTOMapper.apply(student);

        List<UpcomingEventDto> upcomingEventDtos = new ArrayList<>();

        for (StudentTestStatusDTO studentTestStatusDTO : studentTestStatusDTOS) {
            int eventInDays = daysUntil(studentTestStatusDTO.getStartTime());

            if (eventInDays >= 0) {
                String eventName = "מבחן: " + studentTestStatusDTO.getSubject() + " " + studentTestStatusDTO.getTopic();
                upcomingEventDtos.add(new UpcomingEventDto(eventName, eventInDays));
            }
        }

        upcomingEventDtos.addAll(fetchHebrewHolidayEvents());

        upcomingEventDtos.sort(Comparator.comparingInt(UpcomingEventDto::getEventInDays));

        return upcomingEventDtos;

    }
}
