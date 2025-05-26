package org.example.learningprojectserver.dto;

public class UpcomingEventDto {
    private String eventName;
    private int eventInDays;

    public UpcomingEventDto(String eventName, int eventInDays) {
        this.eventName = eventName;
        this.eventInDays = eventInDays;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getEventInDays() {
        return eventInDays;
    }

    public void setEventInDays(int eventInDays) {
        this.eventInDays = eventInDays;
    }

    @Override
    public String toString() {
        return "EventDto{" +
                "eventName='" + eventName + '\'' +
                ", eventInDays=" + eventInDays +
                '}';
    }
}
