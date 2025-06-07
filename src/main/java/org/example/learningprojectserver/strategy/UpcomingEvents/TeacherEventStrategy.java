package org.example.learningprojectserver.strategy.UpcomingEvents;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.TeacherEntityToUpcomingEventDTOMapper;
import org.example.learningprojectserver.repository.TeacherRepository;
import org.example.learningprojectserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class TeacherEventStrategy implements UpcomingEventStrategy{

    private final TeacherEntityToUpcomingEventDTOMapper teacherEntityToUpcomingEventDTOMapper;
    private final TeacherRepository teacherRepository;


    @Override
    public Role supports() {
        return Role.TEACHER;
    }

    @Override
    public List<UpcomingEventDto> findUpcomingEvents(String userId) {

        List<UpcomingEventDto> upcomingEventDtos=teacherEntityToUpcomingEventDTOMapper.apply(teacherRepository.findByUserId(userId));

        return upcomingEventDtos;
    }

}
