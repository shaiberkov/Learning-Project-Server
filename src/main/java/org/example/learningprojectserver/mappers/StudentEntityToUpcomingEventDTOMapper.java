package org.example.learningprojectserver.mappers;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.StudentTestStatusDTO;
import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.entities.StudentEntity;
import org.example.learningprojectserver.repository.StudentRepository;
import org.example.learningprojectserver.utils.HolidayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.example.learningprojectserver.utils.HolidayUtils.daysUntil;
import static org.example.learningprojectserver.utils.HolidayUtils.fetchHebrewHolidayEvents;

@Component
@RequiredArgsConstructor
public class StudentEntityToUpcomingEventDTOMapper implements Mapper<String, List<UpcomingEventDto>>{

    private final StudentRepository studentRepository;

    @Override
    public List<UpcomingEventDto> apply(String studentId) {
        List<StudentTestStatusDTO> studentTestStatusDTOS = studentRepository.findAllTestsForStudent(studentId);

        List<UpcomingEventDto> upcomingEventDtos = new ArrayList<>();

        for (StudentTestStatusDTO studentTestStatusDTO : studentTestStatusDTOS) {
            int eventInDays = daysUntil(studentTestStatusDTO.getStartTime());

            if (eventInDays >= 0) {
                String eventName = "מבחן: " + studentTestStatusDTO.getSubject() + " " + studentTestStatusDTO.getTopic();
                upcomingEventDtos.add(new UpcomingEventDto(eventName, eventInDays));
            }
        }

        upcomingEventDtos.addAll(HolidayUtils.getCachedEvents());

        upcomingEventDtos.sort(Comparator.comparingInt(UpcomingEventDto::getEventInDays));

        return upcomingEventDtos;

    }
}
