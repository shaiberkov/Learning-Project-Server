package org.example.learningprojectserver.service;

import jakarta.annotation.PostConstruct;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.repository.QuestionHistoryRepository;
import org.example.learningprojectserver.repository.QuestionRepository;
import org.example.learningprojectserver.repository.SessionRepository;
import org.example.learningprojectserver.repository.UserProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class StatisticsService {

   private SessionRepository sessionRepository;
   private QuestionHistoryRepository questionHistoryRepository;
   private ActiveUserService activeUserService;
   private final ConcurrentMap<String, UserStatisticsEntity> userStats = new ConcurrentHashMap<>();
@Autowired
   private StatisticsService(SessionRepository sessionRepository ,QuestionHistoryRepository questionHistoryRepository ,ActiveUserService activeUserService){
       this.sessionRepository = sessionRepository;
       this.questionHistoryRepository = questionHistoryRepository;
       this.activeUserService = activeUserService;
   }

//   public UserStatisticsEntity getUserStatistics( String userName) {
//        UserStatisticsEntity userStatistics = new UserStatisticsEntity();
//        userStatistics.setLoginStreak(countConsecutiveActiveDays(userName));
//        userStatistics.setUserLearningStatistics(generateUserLearningStatsMap(userName));
//
//     return userStatistics;
//   }

//    @PostConstruct
//    public void init() {
//
//
//    }


//    @Scheduled(fixedRate = 1000) // מתעדכן רק למשתמשים מחוברים
//    public void refreshStatistics() {
//        userStats.keySet().removeIf(userName -> !activeUserService.isUserActive(userName));
//        for (String userName: activeUserService.getActiveUsers().keySet()) {
//            UserStatisticsEntity userStatistics = getUserStatistics(userName);
//            userStats.put(userName,userStatistics);
//        }
//    }




    private int countConsecutiveActiveDays(String username) {
        List<Date> activeDays = sessionRepository.findDistinctActiveDays(username);

        if (activeDays.isEmpty()) return 0;

        int consecutiveDays = 1;
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < activeDays.size() - 1; i++) {
            calendar.setTime(activeDays.get(i));
            calendar.add(Calendar.DAY_OF_MONTH, -1);

            if (!calendar.getTime().equals(activeDays.get(i + 1))) {
                break;
            }

            consecutiveDays++;
        }

        return consecutiveDays;
    }
//
//public Map<String, Map<String, Map<String, Map<String, Integer>>>> generateUserLearningStatsMap(String userName) {
//    Map<String, Map<String, Map<String, Map<String, Integer>>>> userLearningStatsMap = new HashMap<>();
//    QuestionHistoryEntity questionHistoryEntity = questionHistoryRepository.findByUserName(userName);
//    Map<QuestionEntity, Boolean> questionEntityMap = questionHistoryEntity.getAnsweredQuestions();
//
//    for (Map.Entry<QuestionEntity, Boolean> entry : questionEntityMap.entrySet()) {
//        QuestionEntity questionEntity = entry.getKey();
//        Boolean isAnsweredCorrectly = entry.getValue();
//
//        String subject = questionEntity.getSubject();
//        String topic = questionEntity.getTopic();
//        String subTopic = questionEntity.getSubTopic();
//
//        if (!userLearningStatsMap.containsKey(subject)) {
//            userLearningStatsMap.put(subject, new HashMap<>());
//        }
//        Map<String, Map<String, Map<String, Integer>>> topicMap = userLearningStatsMap.get(subject);
//        if (!topicMap.containsKey(topic)) {
//            topicMap.put(topic, new HashMap<>());
//        }
//        Map<String, Map<String, Integer>> subTopicMap = topicMap.get(topic);
//        if (!subTopicMap.containsKey(subTopic)) {
//            subTopicMap.put(subTopic, new HashMap<>());
//        }
//        Map<String, Integer> correctAnswerMap = subTopicMap.get(subTopic);
//        String resultKey = isAnsweredCorrectly ? "correct" : "incorrect";
//        correctAnswerMap.put(resultKey, correctAnswerMap.getOrDefault(resultKey, 0) + 1);
//    }
//    return userLearningStatsMap;
//}

    public UserStatisticsEntity getUserStats(String userName) {
        return userStats.get(userName);
    }
}
