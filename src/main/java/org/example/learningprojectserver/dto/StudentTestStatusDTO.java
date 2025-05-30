package org.example.learningprojectserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentTestStatusDTO {
    private Long testId;
    private String subject;
    private String Topic;
    private int score;
    private int timeLimitMinutes;
    private String startTime;

}
