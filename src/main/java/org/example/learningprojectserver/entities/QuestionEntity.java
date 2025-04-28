package org.example.learningprojectserver.entities;

import jakarta.persistence.*;


@Entity
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;   // מקצוע (למשל, מתמטיקה)
    private String topic;     // נושא (למשל, שברים)
    private String subTopic;  // תת-נושא (למשל, חיבור שברים)
    private String questionText;
    private String answer;

    @ManyToOne
    @JoinColumn(name = "user_progress_id")
    private UserProgressEntity userProgressEntity;

    @ManyToOne
    @JoinColumn(name = "question_history_id")
    private QuestionHistoryEntity questionHistory;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = true)
    private TestEntity test;

    public QuestionHistoryEntity getQuestionHistory() {
        return questionHistory;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }

    public void setQuestionHistory(QuestionHistoryEntity questionHistory) {
        this.questionHistory = questionHistory;
    }

    public QuestionEntity(String subject, String topic, String subTopic, String questionText, String answer) {
        this.subject = subject;
        this.topic = topic;
        this.subTopic = subTopic;
        this.questionText = questionText;
        this.answer = answer;
    }

    public QuestionEntity() {

    }

    // Getters and Setters
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public UserProgressEntity getUserProgressEntitiy() {
        return userProgressEntity;
    }

    public void setUserProgressEntitiy(UserProgressEntity userProgressEntity) {
        this.userProgressEntity = userProgressEntity;
    }

    public UserProgressEntity getUserProgressEntity() {
        return userProgressEntity;
    }

    public void setUserProgressEntity(UserProgressEntity userProgressEntity) {
        this.userProgressEntity = userProgressEntity;
    }

    public Long getId() {
        return id;
    }

    //@Override
//    public String toString() {
//        return "QuestionEntity{" +
//                "id=" + id +
//                ", subject='" + subject + '\'' +
//                ", topic='" + topic + '\'' +
//                ", subTopic='" + subTopic + '\'' +
//                ", questionText='" + questionText + '\'' +
//                ", answer='" + answer + '\'' +
//                ", userProgressEntity=" + userProgressEntity +
//                ", questionHistory=" + questionHistory +
//                '}';
//    }
}
