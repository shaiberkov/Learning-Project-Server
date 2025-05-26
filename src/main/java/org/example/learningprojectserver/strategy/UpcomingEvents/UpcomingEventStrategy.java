package org.example.learningprojectserver.strategy.UpcomingEvents;

import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.enums.Role;

import java.util.List;

public interface UpcomingEventStrategy {
    Role supports();
    List<UpcomingEventDto> findUpcomingEvents(String userId);
}
