package org.example.learningprojectserver.service;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.QuestionDTO;
import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.repository.StudentQuestionHistoryRepository;
import org.example.learningprojectserver.repository.QuestionRepository;
import org.example.learningprojectserver.repository.StudentProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private QuestionRepository questionRepository;
    private StudentQuestionHistoryRepository studentQuestionHistoryRepository;
    private StudentProgressRepository userprogressRepository;








//    public QuestionDTO generateQuestion(String userName, String subject, String topic, String subTopic) {
//        // שליפת ההתקדמות של המשתמש (חובה שהרשומה קיימת)
//        UserProgressEntity userProgressEntity = userprogressRepository.findByUserName(userName);
//        System.out.println(userProgressEntity);
//        if (userProgressEntity == null) {
//            //throw new RuntimeException("User progress record not found for user: " + userName);
//        }
//
//        Map<String, Integer> skillLevelsBySubTopic = userProgressEntity.getSkillLevelsBySubTopic();
//        System.out.println(skillLevelsBySubTopic);
//
//        // אם המשתמש אף פעם לא ענה על שאלה מהסוג הזה, נוסיף אותו עם רמה 1
//        skillLevelsBySubTopic.putIfAbsent(subTopic, 1);
//
//        // עדכון הנתונים ושמירתם
//        userProgressEntity.setSkillLevelsBySubTopic(skillLevelsBySubTopic);
//        userprogressRepository.save(userProgressEntity);
//
//        // קבלת הרמה של המשתמש
//        int level = skillLevelsBySubTopic.get(subTopic);
//        // יצירת שאלה באמצעות מחולל השאלות המתאים
//        SubjectQuestionGenerator generator = QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);
//        QuestionGenerator questionGenerator = generator.getQuestionGenerator(topic, subTopic);
//        QuestionEntity questionEntity = questionGenerator.generateQuestion(level);
//
//        // שליפת היסטוריית השאלות של המשתמש
//        QuestionHistoryEntity historyEntity = studentQuestionHistoryRepository.findByUserName(userName);
//
//        // **עדכון המפתחות הזרים** לפני שמירת השאלה
//        questionEntity.setQuestionHistory(historyEntity);
//        questionEntity.setUserProgressEntitiy(userProgressEntity);
//
//        // סינון תווי LRM מטקסט השאלה לפני שמירה
//        String questionTextWithoutLRM = questionEntity.getQuestionText().replaceAll("[\u200E\u200F]", "");
//        questionEntity.setQuestionText(questionTextWithoutLRM);
//
//        // סינון תווי LRM מהתשובה לפני שמירה
//        String answerWithoutLRM = questionEntity.getAnswer().replaceAll("[\u200E\u200F]", "");
//        questionEntity.setAnswer(answerWithoutLRM);
//
//        // שמירת השאלה במסד הנתונים
//        questionEntity = questionRepository.save(questionEntity);
//
//        // החזרת השאלה כ-DTO
//        return mapToDTO(questionEntity);
//    }



    //TODO להשאיר רמה מקסימלית ל5
//    public SubmitAnswerResponse submitAnswer(String userName,Long id, String subject, String topic, String subTopic, String questionText, String answer) {
//
//        // חיפוש היסטוריית השאלות של המשתמש
//        QuestionHistoryEntity questionHistoryEntity = questionHistoryRepository.findByUserName(userName);
//        // חיפוש נתוני התקדמות של המשתמש
//        UserProgressEntity userProgressEntity = userprogressRepository.findByUserName(userName);
//
//        // אם נתוני המשתמש או היסטוריית השאלות לא נמצאו, החזר תשובה מתאימה
////        if (questionHistoryEntity == null || userProgressEntity == null) {
////            return new SubmitAnswerResponse(false, "User data not found.");
////        }
//
//
//        // חיפוש השאלה במסד הנתונים
//        QuestionEntity questionEntity = questionRepository.findQuestionById(id);
//        // בדיקת נכונות התשובה
//        boolean isCorrect = questionEntity.getAnswer().equalsIgnoreCase(answer.trim());
//        System.out.println(questionEntity.getAnswer() + "  " + answer.trim());
//        // עדכון היסטוריית השאלות של המשתמש
//        questionHistoryEntity.addAnsweredQuestion(questionEntity, isCorrect);
//        questionHistoryRepository.save(questionHistoryEntity); // שמירת היסטוריית השאלות
//
//        // עדכון רצף ההצלחה של המשתמש בנושא ספציפי
//        Map<String, Integer> subTopicSuccessStreak = userProgressEntity.getSubTopicSuccessStreak();
//        int currentSuccesStreak = subTopicSuccessStreak.getOrDefault(subTopic, 0);
//        // עדכון אם התשובה נכונה או לא
//        Map<String, Integer> skillLevelsBySubTopic = userProgressEntity.getSkillLevelsBySubTopic();;
//        Map<String, Integer> subTopicIncorrectStreak = userProgressEntity.getSubTopicIncorrectStreak();
//        int currentIncorrectStreak = subTopicIncorrectStreak.getOrDefault(subTopic, 0);
//        if (isCorrect) {
//            subTopicIncorrectStreak.put(subTopic, 0);
//            subTopicSuccessStreak.put(subTopic, currentSuccesStreak + 1);
//
//            if (subTopicSuccessStreak.get(subTopic) == 3) {
//                //skillLevelsBySubTopic = userProgressEntity.getSkillLevelsBySubTopic();
//                skillLevelsBySubTopic.put(subTopic, skillLevelsBySubTopic.get(subTopic) + 1);
//                subTopicSuccessStreak.put(subTopic, 0);
//            }
//        } else {
//
//            subTopicSuccessStreak.put(subTopic, 0);
//            subTopicIncorrectStreak.put(subTopic,currentIncorrectStreak+1 );
//            if (subTopicIncorrectStreak.get(subTopic) == 3) {
//                if(skillLevelsBySubTopic.get(subTopic)>1){
//                    //skillLevelsBySubTopic = userProgressEntity.getSkillLevelsBySubTopic();
//                    skillLevelsBySubTopic.put(subTopic, skillLevelsBySubTopic.get(subTopic) -1);
//                    subTopicIncorrectStreak.put(subTopic, 0);
//                }
//
//
//            }
//        }
//        userProgressEntity.setSkillLevelsBySubTopic(skillLevelsBySubTopic);
//        // שמירה של התקדמות המשתמש
//        userProgressEntity.setSubTopicSuccessStreak(subTopicSuccessStreak);
//        userProgressEntity.setSubTopicIncorrectStreak(subTopicIncorrectStreak);
//        userprogressRepository.save(userProgressEntity);
//
//        // החזרת תשובה ללקוח(true,isCorrect, isCorrect ? "תשובה נכונה" : "Incorrect answer.");
//        SubmitAnswerResponse submitAnswerResponse=new SubmitAnswerResponse();
//        submitAnswerResponse.setSuccess(true);
//        System.out.println(isCorrect);
//        submitAnswerResponse.setCorrect(isCorrect);
//        submitAnswerResponse.setMessage(isCorrect ? "תשובה נכונה כל הכבוד" : "תשובה לא נכונה ");
//        return submitAnswerResponse;
//    }


}
