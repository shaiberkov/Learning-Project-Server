package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.example.learningprojectserver.entities.SchoolManagerEntity;
import org.example.learningprojectserver.entities.SystemAdminEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.example.learningprojectserver.utils.HolidayUtils.fetchHebrewHolidayEvents;
@Component
public class SystemAdmitEntityToUpcomingEventDTOMapper implements Mapper<SystemAdminEntity, List<UpcomingEventDto>> {
    @Override
    public List<UpcomingEventDto> apply(SystemAdminEntity systemAdminEntity) {
        List<UpcomingEventDto> upcomingEventDtos = new ArrayList<>();
        upcomingEventDtos.addAll(fetchHebrewHolidayEvents());
        upcomingEventDtos.sort(Comparator.comparingInt(UpcomingEventDto::getEventInDays));

        return upcomingEventDtos;
    }
}
