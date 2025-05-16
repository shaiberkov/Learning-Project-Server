package org.example.learningprojectserver.dto;


public class QuestionDTO {
    private Long id;
    private String subject;   // מקצוע (למשל, מתמטיקה)
    private String topic;     // נושא (למשל, שברים)
    private String subTopic;  // תת-נושא (למשל, חיבור שברים)
    private String questionText;

    public QuestionDTO(Long id, String subject, String topic, String subTopic, String questionText) {
        this.id = id;
        this.subject = subject;
        this.topic = topic;
        this.subTopic = subTopic;
        this.questionText = questionText;
    }
    public QuestionDTO() {}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", topic='" + topic + '\'' +
                ", subTopic='" + subTopic + '\'' +
                ", questionText='" + questionText + '\'' +
                '}';
    }
}
