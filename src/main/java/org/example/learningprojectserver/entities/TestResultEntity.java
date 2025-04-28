package org.example.learningprojectserver.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class TestResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;
    private int score;
    private int correctAnswers;
    private int incorrectAnswers;
    @OneToMany(cascade = CascadeType.ALL)
    private List<QuestionEntity> correctQuestions;
    @OneToMany(cascade = CascadeType.ALL)
    private List<QuestionEntity> incorrectQuestions;


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public void setCorrectQuestions(List<QuestionEntity> correctQuestions) {
        this.correctQuestions = correctQuestions;
    }

    public void setIncorrectQuestions(List<QuestionEntity> incorrectQuestions) {
        this.incorrectQuestions = incorrectQuestions;
    }

    public TestResultEntity() {

    }

    public int getScore() {
        return score;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public List<QuestionEntity> getCorrectQuestions() {
        return correctQuestions;
    }

    public List<QuestionEntity> getIncorrectQuestions() {
        return incorrectQuestions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
