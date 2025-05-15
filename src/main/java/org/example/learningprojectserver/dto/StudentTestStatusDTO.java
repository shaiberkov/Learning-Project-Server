package org.example.learningprojectserver.dto;

public class StudentTestStatusDTO {
    private Long testId;
    private String subject;
    private String Topic;
    private int score;
    private int timeLimitMinutes;
    private String startTime;       // פורמט: "yyyy-MM-dd HH:mm"


    public StudentTestStatusDTO() {
    }

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

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserTestStatusDTO{" +
                "testId=" + testId +
                ", subject='" + subject + '\'' +
                ", Topic='" + Topic + '\'' +
                ", score=" + score +
                '}';
    }
}
