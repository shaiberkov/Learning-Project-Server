package org.example.learningprojectserver.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id") // קשר Many-to-One (רבים ליחיד)
    private StudentEntity student;


    @ManyToOne
    @JoinColumn(name = "teacher_id") // קשר ל-TeacherEntity
    private TeacherEntity teacher;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionEntity> questions;

    private String subject;
    private String topic;
    private String difficulty;
    private int questionCount;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
//                ", user=" + user +
                ", questions=" + questions +
                ", subject='" + subject + '\'' +
                ", topic='" + topic + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", questionCount=" + questionCount +
                '}';
    }

    public String getSubject() {
        return subject;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public TeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public TestEntity() {}
//
//    public UserEntity getUser() {
//        return user;
//    }

//    public void setUser(UserEntity user) {
//        this.user = user;
//    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

//    public void evaluateTest(List<Boolean> answers) {
//        int totalQuestions = questions.size();
//        correctAnswers = (int) answers.stream().filter(Boolean::booleanValue).count();
//        incorrectAnswers = totalQuestions - correctAnswers;
//        score = (int) (((double) correctAnswers / totalQuestions) * 100);
//    }
