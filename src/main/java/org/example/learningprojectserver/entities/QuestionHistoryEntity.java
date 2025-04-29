package org.example.learningprojectserver.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class QuestionHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // קשר OneToOne עם UserEntity
    @OneToOne
    @JoinColumn(name = "student_id") // או שם אחר שתבחר
    private StudentEntity student;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "answered_questions", joinColumns = @JoinColumn(name = "history_id"))
    @MapKeyJoinColumn(name = "question_id")
    @Column(name = "is_answered_correct")
    private Map<QuestionEntity, Boolean> answeredQuestions;

     public QuestionHistoryEntity() {
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

    // Getters and Setters
    public int getId() {
        return id;
    }

//    public UserEntity getUser() {
//        return user;
//    }
//
//    public void setUser(UserEntity user) {
//        this.user = user;
//    }

    public Map<QuestionEntity, Boolean> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(Map<QuestionEntity, Boolean> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }



    //@Override
//    public String toString() {
//        return "QuestionHistoryEntity{" +
//                "id=" + id +
//
//                ", answeredQuestions=" + answeredQuestions +
//
//                '}';
//    }
}
