package org.example.learningprojectserver.entities;

public class QuestionEntity {

    private String subject;
    private String subSubject;
    private String questionText;
    private String answer;


    public QuestionEntity(String subject, String subSubject, String questionText, String answer) {
        this.subject = subject;
        this.subSubject = subSubject;
        this.questionText = questionText;
        this.answer = answer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubSubject() {
        return subSubject;
    }

    public void setSubSubject(String subSubject) {
        this.subSubject = subSubject;
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

