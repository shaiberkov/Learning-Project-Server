package org.example.learningprojectserver.strategy.UpcomingEvents;

import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.dto.StudentTestStatusDTO;
import org.example.learningprojectserver.entities.StudentEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.StudentEntityToStudentTestStatusDTOMapper;
import org.example.learningprojectserver.mappers.StudentEntityToUpcomingEventDTOMapper;
import org.example.learningprojectserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.example.learningprojectserver.utils.HolidayUtils.daysUntil;
import static org.example.learningprojectserver.utils.HolidayUtils.fetchHebrewHolidayEvents;

@Component
public class StudentEventStrategy implements UpcomingEventStrategy {

private final UserRepository userRepository;
private final StudentEntityToUpcomingEventDTOMapper studentEntityToUpcomingEventDTOMapper;

    @Autowired
    public StudentEventStrategy(UserRepository userRepository, StudentEntityToUpcomingEventDTOMapper studentEntityToUpcomingEventDTOMapper) {
        this.userRepository = userRepository;
        this.studentEntityToUpcomingEventDTOMapper = studentEntityToUpcomingEventDTOMapper;
    }

    @Override
    public Role supports() {
        return Role.STUDENT;
    }


    @Override
    public List<UpcomingEventDto> findUpcomingEvents(String userId) {
        UserEntity user = userRepository.findUserByUserId(userId);
        StudentEntity student = (StudentEntity) user;
        List<UpcomingEventDto> upcomingEventDtos = studentEntityToUpcomingEventDTOMapper.apply(student);

        return upcomingEventDtos;
    }


}
