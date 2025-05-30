package org.example.learningprojectserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private String subject;   // מקצוע (למשל, מתמטיקה)
    private String topic;     // נושא (למשל, שברים)
    private String subTopic;  // תת-נושא (למשל, חיבור שברים)
    private String questionText;

}
