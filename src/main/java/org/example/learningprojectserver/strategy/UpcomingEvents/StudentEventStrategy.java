package org.example.learningprojectserver.strategy.UpcomingEvents;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.entities.StudentEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.StudentEntityToUpcomingEventDTOMapper;
import org.example.learningprojectserver.repository.StudentRepository;
import org.example.learningprojectserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class StudentEventStrategy implements UpcomingEventStrategy {

private final StudentEntityToUpcomingEventDTOMapper studentEntityToUpcomingEventDTOMapper;


    @Override
    public Role supports() {
        return Role.STUDENT;
    }


@Override
public List<UpcomingEventDto> findUpcomingEvents(String userId) {
    return  studentEntityToUpcomingEventDTOMapper.apply(userId);
}


}
