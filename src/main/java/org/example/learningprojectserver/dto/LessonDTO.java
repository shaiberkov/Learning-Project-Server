package org.example.learningprojectserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private String subject;
    private DayOfWeek dayOfWeek;
    private String startTime;
    private String endTime;
    private String classRoomName;
    private String teacherName;


}
