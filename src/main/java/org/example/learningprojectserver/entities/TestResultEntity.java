package org.example.learningprojectserver.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "testResult_type")
public class TestResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;
    private int correctAnswersCount;
    private int incorrectAnswersCount;

    private String finishTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private TestEntity test;


    @OneToMany(fetch = FetchType.LAZY)
    private List<TestQuestionEntity> correctQuestions=new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY)
    private List<TestQuestionEntity> incorrectQuestions=new ArrayList<>();



    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }

    public void setCorrectAnswersCount(int correctAnswers) {
        this.correctAnswersCount = correctAnswers;
    }

    public void setIncorrectAnswersCount(int incorrectAnswers) {
        this.incorrectAnswersCount = incorrectAnswers;
    }

    public List<TestQuestionEntity> getCorrectQuestions() {
        return correctQuestions;
    }

    public void setCorrectQuestions(List<TestQuestionEntity> correctQuestions) {
        this.correctQuestions = correctQuestions;
    }

    public List<TestQuestionEntity> getIncorrectQuestions() {
        return incorrectQuestions;
    }

    public void setIncorrectQuestions(List<TestQuestionEntity> incorrectQuestions) {
        this.incorrectQuestions = incorrectQuestions;
    }

    //    public void setCorrectQuestions(List<QuestionEntity> correctQuestions) {
//        this.correctQuestions = correctQuestions;
//    }
//
//    public void setIncorrectQuestions(List<QuestionEntity> incorrectQuestions) {
//        this.incorrectQuestions = incorrectQuestions;
//    }

//    public List<QuestionEntity> getCorrectQuestions() {
//        return correctQuestions;
//    }
//
//    public void setCorrectQuestions(List<QuestionEntity> correctQuestions) {
//        this.correctQuestions = correctQuestions;
//    }
//
//    public List<QuestionEntity> getIncorrectQuestions() {
//        return incorrectQuestions;
//    }
//
//    public void setIncorrectQuestions(List<QuestionEntity> incorrectQuestions) {
//        this.incorrectQuestions = incorrectQuestions;
//    }

    public TestResultEntity() {

    }

    public int getScore() {
        return score;
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public int getIncorrectAnswersCount() {
        return incorrectAnswersCount;
    }

//    public List<QuestionEntity> getCorrectQuestions() {
//        return correctQuestions;
//    }
//
//    public List<QuestionEntity> getIncorrectQuestions() {
//        return incorrectQuestions;
//    }

    public void setId(Long id) {
        this.id = id;
    }

//    public StudentEntity getStudent() {
//        return student;
//    }
//
//    public void setStudent(StudentEntity student) {
//        this.student = student;
//    }

    public Long getId() {
        return id;
    }
}
