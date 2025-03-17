package org.example.learningprojectserver.response;

public class SubmitAnswerResponse extends BasicResponse {

    private boolean isCorrect;   // משתנה לציון אם התשובה נכונה
    private String message;      // הודעה מתאימה למשתמש

    public SubmitAnswerResponse(boolean success, String errorCode, boolean isCorrect, String message) {
        super(success, errorCode);
        this.isCorrect = isCorrect;
        this.message = message;
    }
    public SubmitAnswerResponse() {

    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

