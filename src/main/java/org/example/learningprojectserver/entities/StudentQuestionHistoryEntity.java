package org.example.learningprojectserver.entities;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity

public class StudentQuestionHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentEntity student;


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "answered_questions", joinColumns = @JoinColumn(name = "history_id"))
    @MapKeyJoinColumn(name = "question_id")
    @Column(name = "is_answered_correct")
    private Map<QuestionEntity, Boolean> answeredQuestions;

     public StudentQuestionHistoryEntity() {
         this.answeredQuestions = new HashMap<>();

     }


    public void addAnsweredQuestion(QuestionEntity question, Boolean isAnsweredCorrect) {
        this.answeredQuestions.put(question, isAnsweredCorrect);
    }

    public void setId(int id) {
        this.id = id;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public int getId() {
        return id;
    }


    public Map<QuestionEntity, Boolean> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(Map<QuestionEntity, Boolean> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }


}
