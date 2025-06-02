package org.example.learningprojectserver.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("PRACTICE")
@Getter
@Setter
@NoArgsConstructor
public class PracticeQuestionEntity extends QuestionEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_progress_id")
    private StudentProgressEntity progressEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_history_id")
    private StudentQuestionHistoryEntity questionHistory;

}