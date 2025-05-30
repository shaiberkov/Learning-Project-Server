package org.example.learningprojectserver.entities;

import java.util.Map;

public class StudentStatisticsEntity {

    private int loginStreak;
    private Map<String, Map<String, Map<String, Map<String, Integer>>>> userLearningStatistics;

    public Map<String, Map<String, Map<String, Map<String, Integer>>>> getUserLearningStatistics() {
        return userLearningStatistics;
    }

    public void setUserLearningStatistics(Map<String, Map<String, Map<String, Map<String, Integer>>>> userLearningStatistics) {
        this.userLearningStatistics = userLearningStatistics;
    }

    public StudentStatisticsEntity() {}



     public int getLoginStreak() {
         return loginStreak;
     }

     public void setLoginStreak(int loginStreak) {
         this.loginStreak = loginStreak;
     }

    @Override
    public String toString() {
        return "UserStatisticsEntity{" +
                "loginStreak=" + loginStreak +
                ", userLearningStatistics=" + userLearningStatistics +
                '}';
    }
}
