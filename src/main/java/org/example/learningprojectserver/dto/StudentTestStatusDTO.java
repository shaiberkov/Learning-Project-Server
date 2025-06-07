
package org.example.learningprojectserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Getter
@Setter
@NoArgsConstructor

public class StudentTestStatusDTO {
    private Long testId;
    private String subject;
    private String Topic;
    private int score;
    private String startTime;
    private int timeLimitMinutes;
    public StudentTestStatusDTO(Long testId, String subject, String topic, int score, String startTime, int timeLimitMinutes) {
        this.testId = testId;
        this.subject = subject;
        this.Topic = topic;
        this.startTime = startTime;
        this.timeLimitMinutes = timeLimitMinutes;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime endDateTime = startDateTime.plusMinutes(timeLimitMinutes);

        if (score == -1 && endDateTime.isBefore(LocalDateTime.now())) {
            this.score = 0;
        } else {
            this.score = score;
        }
    }


}
