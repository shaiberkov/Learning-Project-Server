package org.example.learningprojectserver.strategy.UpcomingEvents;

import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.entities.SchoolManagerEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.SchoolManagerEntityToUpcomingEventDTOMapper;
import org.example.learningprojectserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SchoolManagerEventStrategy implements UpcomingEventStrategy{
    private final UserRepository userRepository;
    private final SchoolManagerEntityToUpcomingEventDTOMapper schoolManagerEntityToUpcomingEventDTOMapper;
@Autowired
    public SchoolManagerEventStrategy(UserRepository userRepository, SchoolManagerEntityToUpcomingEventDTOMapper schoolManagerEntityToUpcomingEventDTOMapper) {
        this.userRepository = userRepository;
    this.schoolManagerEntityToUpcomingEventDTOMapper = schoolManagerEntityToUpcomingEventDTOMapper;
}

    @Override
    public Role supports() {
        return Role.SCHOOLMANAGER;
    }

    @Override
    public List<UpcomingEventDto> findUpcomingEvents(String userId) {
        UserEntity user = userRepository.findUserByUserId(userId);
        SchoolManagerEntity schoolManagerEntity= (SchoolManagerEntity) user;
        List<UpcomingEventDto> upcomingEventDtos = schoolManagerEntityToUpcomingEventDTOMapper.apply(schoolManagerEntity);

        return upcomingEventDtos;
    }
}
