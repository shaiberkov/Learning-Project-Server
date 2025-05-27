package org.example.learningprojectserver.service;

import jakarta.annotation.PostConstruct;
import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.strategy.UpcomingEvents.UpcomingEventStrategy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UpcomingEventService {

    private final Map<Role, UpcomingEventStrategy> strategyMap;

    public UpcomingEventService(List<UpcomingEventStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(UpcomingEventStrategy::supports, s -> s));
    }



    @Cacheable(value = "upcomingEvents", key = "#role + '_' + #userId")
    public List<UpcomingEventDto> getUpcomingEvents(Role role, String userId) {
        UpcomingEventStrategy strategy = strategyMap.get(role);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported role: " + role);
        }
        return strategy.findUpcomingEvents(userId);
    }
}
