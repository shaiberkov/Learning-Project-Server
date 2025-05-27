package org.example.learningprojectserver.strategy.UpcomingEvents;

import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.entities.SystemAdminEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.SystemAdmitEntityToUpcomingEventDTOMapper;
import org.example.learningprojectserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SystemAdminEventStrategy implements UpcomingEventStrategy{
    private final UserRepository userRepository;
    private final SystemAdmitEntityToUpcomingEventDTOMapper systemAdmitEntityToUpcomingEventDTOMapper;

@Autowired
    public SystemAdminEventStrategy(UserRepository userRepository, SystemAdmitEntityToUpcomingEventDTOMapper systemAdmitEntityToUpcomingEventDTOMapper) {
        this.userRepository = userRepository;
    this.systemAdmitEntityToUpcomingEventDTOMapper = systemAdmitEntityToUpcomingEventDTOMapper;
}

    @Override
    public Role supports() {
        return Role.SYSTEM_ADMIN;
    }

    @Override
    public List<UpcomingEventDto> findUpcomingEvents(String userId) {
        UserEntity user = userRepository.findUserByUserId(userId);
        SystemAdminEntity systemAdminEntity=(SystemAdminEntity)user;
        List<UpcomingEventDto> upcomingEventDtos = systemAdmitEntityToUpcomingEventDTOMapper.apply(systemAdminEntity);

        return upcomingEventDtos;
    }
}
