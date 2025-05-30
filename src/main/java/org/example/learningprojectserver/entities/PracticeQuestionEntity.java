package org.example.learningprojectserver.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("PRACTICE")
@Getter
@Setter
@NoArgsConstructor
public class PracticeQuestionEntity extends QuestionEntity {

    @ManyToOne
    @JoinColumn(name = "student_progress_id")
    private StudentProgressEntity progressEntity;

    @ManyToOne
    @JoinColumn(name = "question_history_id")
    private StudentQuestionHistoryEntity questionHistory;

}