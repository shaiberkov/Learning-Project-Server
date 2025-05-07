package org.example.learningprojectserver.entities;


import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "test_type")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String topic;
    private String difficulty;
    private int questionCount;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TestQuestionEntity> questions=new ArrayList<>();

    private int timeLimitMinutes;

    private String startTime;       // פורמט: "yyyy-MM-dd HH:mm"




    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getQuestionCount() { return questionCount; }
    public void setQuestionCount(int questionCount) { this.questionCount = questionCount; }

    public List<TestQuestionEntity> getQuestions() { return questions; }
    public void setQuestions(List<TestQuestionEntity> questions) { this.questions = questions; }

    public int getTimeLimitMinutes() {
        return timeLimitMinutes;
    }

    public void setTimeLimitMinutes(int timeLimitMinutes) {
        this.timeLimitMinutes = timeLimitMinutes;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}

//
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//public class TestEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "student_id") // קשר Many-to-One (רבים ליחיד)
//    private StudentEntity student;
//
//
//    @ManyToOne
//    @JoinColumn(name = "teacher_id") // קשר ל-TeacherEntity
//    private TeacherEntity teacher;
//
//    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TestQuestionEntity> questions;
//
//    private String subject;
//    private String topic;
//    private String difficulty;
//    private int questionCount;
//
//    public String getTopic() {
//        return topic;
//    }
//
//    public void setTopic(String topic) {
//        this.topic = topic;
//    }
//
//    @Override
//    public String toString() {
//        return "TestEntity{" +
//                "id=" + id +
////                ", user=" + user +
//                ", questions=" + questions +
//                ", subject='" + subject + '\'' +
//                ", topic='" + topic + '\'' +
//                ", difficulty='" + difficulty + '\'' +
//                ", questionCount=" + questionCount +
//                '}';
//    }
//
//    public String getSubject() {
//        return subject;
//    }
//
//    public StudentEntity getStudent() {
//        return student;
//    }
//
//    public TeacherEntity getTeacher() {
//        return teacher;
//    }
//
//    public void setTeacher(TeacherEntity teacher) {
//        this.teacher = teacher;
//    }
//
//    public void setStudent(StudentEntity student) {
//        this.student = student;
//    }
//
//    public void setSubject(String subject) {
//        this.subject = subject;
//    }
//
//    public String getDifficulty() {
//        return difficulty;
//    }
//
//    public void setDifficulty(String difficulty) {
//        this.difficulty = difficulty;
//    }
//
//    public int getQuestionCount() {
//        return questionCount;
//    }
//
//    public void setQuestionCount(int questionCount) {
//        this.questionCount = questionCount;
//    }
//
//    public TestEntity() {}
////
////    public UserEntity getUser() {
////        return user;
////    }
//
////    public void setUser(UserEntity user) {
////        this.user = user;
////    }
//
//
//    public List<TestQuestionEntity> getQuestions() {
//        return questions;
//    }
//
//    public void setQuestions(List<TestQuestionEntity> questions) {
//        this.questions = questions;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//}
//
////    public void evaluateTest(List<Boolean> answers) {
////        int totalQuestions = questions.size();
////        correctAnswers = (int) answers.stream().filter(Boolean::booleanValue).count();
////        incorrectAnswers = totalQuestions - correctAnswers;
////        score = (int) (((double) correctAnswers / totalQuestions) * 100);
////    }
