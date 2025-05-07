package org.example.learningprojectserver.response;

import java.util.List;

public class TestResultResponse {
    private int score;
    private int correctAnswers;
    private int incorrectAnswers;

    private List<String> correctAnswerList;
    private List<String> incorrectAnswerList;
    public TestResultResponse() {

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public List<String> getCorrectAnswerList() {
        return correctAnswerList;
    }

    public void setCorrectAnswerList(List<String> correctAnswerList) {
        this.correctAnswerList = correctAnswerList;
    }

    public List<String> getIncorrectAnswerList() {
        return incorrectAnswerList;
    }

    public void setIncorrectAnswerList(List<String> incorrectAnswerList) {
        this.incorrectAnswerList = incorrectAnswerList;
    }

    @Override
    public String toString() {
        return "TestResultResponse{" +
                "score=" + score +
                ", correctAnswers=" + correctAnswers +
                ", incorrectAnswers=" + incorrectAnswers +
                ", correctAnswerList=" + correctAnswerList +
                ", incorrectAnswerList=" + incorrectAnswerList +
                '}';
    }
}
