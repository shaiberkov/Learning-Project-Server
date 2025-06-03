package org.example.learningprojectserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class LessonDTO {
    private String subject;
    private DayOfWeek dayOfWeek;
    private String startTime;
    private String endTime;
    private String classRoomName;
    private String teacherName;

    public LessonDTO(String subject, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, String classRoomName, String teacherName) {
        this.subject = subject;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        this.endTime = endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        this.classRoomName = classRoomName;
        this.teacherName = teacherName;
    }

}
