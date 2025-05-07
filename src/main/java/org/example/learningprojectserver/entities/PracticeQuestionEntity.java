package org.example.learningprojectserver.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("PRACTICE")
public class PracticeQuestionEntity extends QuestionEntity {

    @ManyToOne
    @JoinColumn(name = "student_progress_id")
    private StudentProgressEntity progressEntity;

    @ManyToOne
    @JoinColumn(name = "question_history_id")
    private StudentQuestionHistoryEntity questionHistory;

    public PracticeQuestionEntity() {
        super();
    }

    public StudentProgressEntity getProgressEntity() {
        return progressEntity;
    }

    public void setProgressEntity(StudentProgressEntity progressEntity) {
        this.progressEntity = progressEntity;
    }

    public StudentQuestionHistoryEntity getQuestionHistory() {
        return questionHistory;
    }

    public void setQuestionHistory(StudentQuestionHistoryEntity questionHistory) {
        this.questionHistory = questionHistory;
    }
}