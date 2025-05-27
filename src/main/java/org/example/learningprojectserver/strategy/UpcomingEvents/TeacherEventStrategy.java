package org.example.learningprojectserver.strategy.UpcomingEvents;

import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.TeacherEntityToUpcomingEventDTOMapper;
import org.example.learningprojectserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TeacherEventStrategy implements UpcomingEventStrategy{

    private final UserRepository userRepository;
    private final TeacherEntityToUpcomingEventDTOMapper teacherEntityToUpcomingEventDTOMapper;
@Autowired
    public TeacherEventStrategy(UserRepository userRepository, TeacherEntityToUpcomingEventDTOMapper teacherEntityToUpcomingEventDTOMapper) {
        this.userRepository = userRepository;
    this.teacherEntityToUpcomingEventDTOMapper = teacherEntityToUpcomingEventDTOMapper;
}

    @Override
    public Role supports() {
        return Role.TEACHER;
    }

    @Override
    public List<UpcomingEventDto> findUpcomingEvents(String userId) {
        UserEntity user = userRepository.findUserByUserId(userId);
        TeacherEntity teacher = (TeacherEntity) user;
        List<UpcomingEventDto> upcomingEventDtos=teacherEntityToUpcomingEventDTOMapper.apply(teacher);

        return upcomingEventDtos;
    }

}
