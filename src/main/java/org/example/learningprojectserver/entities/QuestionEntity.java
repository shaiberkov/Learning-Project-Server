package org.example.learningprojectserver.entities;

public class QuestionEntity {

    private String subject;   // מקצוע (למשל, מתמטיקה)
    private String topic;     // נושא (למשל, שברים)
    private String subTopic;  // תת-נושא (למשל, חיבור שברים)
    private String questionText;
    private String answer;

    public QuestionEntity(String subject, String topic, String subTopic, String questionText, String answer) {
        this.subject = subject;
        this.topic = topic;
        this.subTopic = subTopic;
        this.questionText = questionText;
        this.answer = answer;
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
}
