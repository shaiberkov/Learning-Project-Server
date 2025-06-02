package org.example.learningprojectserver.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.*;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.*;
import org.example.learningprojectserver.repository.*;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.SubmitAnswerResponse;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGeneratorFactory;
import org.example.learningprojectserver.service.QuestionGenerator.SubjectQuestionGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class StudentService {

private final StudentProgressRepository studentProgressRepository;
private final StudentQuestionHistoryRepository studentQuestionHistoryRepository;
private final QuestionRepository questionRepository;
private final UserRepository userRepository;
private final PracticeTestRepository practiceTestRepository;
private final ClassRoomRepository classRoomRepository;
private final TestEntityToTestDTOMapper testEntityToTestDTOMapper;
private final QuestionEntityToQuestionDTOMapper questionEntityToQuestionDTOMapper;
private final QuestionEntityToPracticeQuestionMapper questionEntityToPracticeQuestionMapper;
private final SchoolRepository schoolRepository;
private final LessonsToScheduleMapper lessonsToScheduleMapper;
private final ChatGptService chatGptService;
private final StudentEntityToStudentTestStatusDTOMapper studentEntityToStudentTestStatusDTOMapper;
private final TestService testService;
    private final StudentRepository studentRepository;


    @Cacheable(value = "studentTestsStatus", key = "#userId")
    public List<StudentTestStatusDTO> getStudentTestsStatus(String userId) {
        UserEntity user= userRepository.findUserByUserId(userId);
        if (!(user.getRole()== Role.STUDENT)){
            return new ArrayList();
        }
        StudentEntity student = (StudentEntity) user;

        List<StudentTestStatusDTO> studentTestStatusDTOS = studentEntityToStudentTestStatusDTOMapper.apply(student);
    return studentTestStatusDTOS;
    }

    @Cacheable(value = "studentSchedule", key = "#studentId")
public BasicResponse getStudentSchedule(String schoolCode, String studentId){

//    SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
//    if (school == null) {
//        return new BasicResponse(false, "בית הספר לא נמצא לפי הקוד שסופק");
//    }

    UserEntity user = userRepository.findUserByUserId(studentId);
    if (user == null || user.getRole() != Role.STUDENT) {
        return new BasicResponse(false, "המשתמש לא נמצא או שאינו תלמיד");
    }
    StudentEntity student = (StudentEntity) user;

    List<LessonEntity> studentsLessons=student.getClassRoom().getSchedule().getLessons();

    Map<DayOfWeek, List<LessonDTO>> lessonsByDay = lessonsToScheduleMapper.apply(studentsLessons);

    BasicResponse response = new BasicResponse(true, null);
    response.setData(lessonsByDay);
return response;
}



public void sendJobMessage(String userId,String subject,String subTopic) {
    String job = String.format("""
            אתה מורה פרטי ל%s שמדבר עברית ורק עברית.
            אתה מסביר בצורה פשוטה וברורה, כך שגם ילדים יוכלו להבין אותך בקלות.
            אם תקבל שאלה לפתרון מהסוג: "%s",
            אתה תסביר לתלמיד איך לחשוב על הפתרון,
            אבל בשום פנים ואופן **לא תגלה לו את התשובה!**
            רק תדריך אותו צעד-צעד איך לגשת לפתרון בצורה קלה להבנה.
            תשתמש בדוגמאות פשוטות ותסביר במילים ברורות, בלי מונחים מסובכים,
            וכל הודעה שלך תישלח רק דרך פתרון אחת כדי שהתלמיד לא יקבל הודעה ארוכה ולא מובנת.
            אם התלמיד לא מבין, תסביר שוב בדרך אחרת, אפילו עם דוגמה מהחיים האמיתיים.
            כל שאלה שלא קשורה למה שעכשיו ציינתי – אל תענה,
            ותזכיר לתלמיד שאתה מורה ל%s שבא לעזור לו בשאלות מהסוג שקיבלת בפורמט הזה.
        """, subject, subTopic, subject);
    chatGptService.initializeConversationWithJob(userId, job);

}
//
//    public BasicResponse generateQuestionForPractice(String userId, String subject, String topic, String subTopic) {
//        sendJobMessage(userId,subject,subTopic);
//
//       UserEntity user= userRepository.findUserByUserId(userId);
//       if(user == null) {
//           return new BasicResponse(false,"יוזר לא קיים");
//       }
//       if (!(user.getRole()== Role.STUDENT)){
//           return new BasicResponse(false,"יוזר זה אינו תלמיד");
//       }
////       StudentEntity student=(StudentEntity)user;
//
//       ClassRoomEntity classRoom=classRoomRepository.findClassRoomOfUserByUserId(userId);
//        if(classRoom == null){
//            return new BasicResponse(false,",תלמיד עוד לא משובץ לכיתה");
//        }
//
//        StudentProgressEntity studentProgressEntity = studentProgressRepository.findStudentProgressByUserId(userId);
//
//
//        Map<String, Integer> skillLevelsBySubTopic = studentProgressEntity.getSkillLevelsBySubTopic();
//
//        skillLevelsBySubTopic.putIfAbsent(subTopic, 1);
//
//        studentProgressEntity.setSkillLevelsBySubTopic(skillLevelsBySubTopic);
//        studentProgressRepository.save(studentProgressEntity);
//
//        int level = skillLevelsBySubTopic.get(subTopic);
//        SubjectQuestionGenerator generator = QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);
//        QuestionGenerator questionGenerator = generator.getQuestionGenerator(topic, subTopic);
//
//        QuestionEntity baseQuestion = questionGenerator.generateQuestion(level);
//
//        PracticeQuestionEntity questionEntity=questionEntityToPracticeQuestionMapper.apply(baseQuestion);
//
//        StudentQuestionHistoryEntity historyEntity = studentQuestionHistoryRepository.findStudentQuestionHistoryByUserId(userId);
//
//        questionEntity.setQuestionHistory(historyEntity);
//        questionEntity.setProgressEntity(studentProgressEntity);
//
//        String questionTextWithoutLRM = questionEntity.getQuestionText().replaceAll("[\u200E\u200F]", "");
//        questionEntity.setQuestionText(questionTextWithoutLRM);
//
//        String answerWithoutLRM = questionEntity.getAnswer().replaceAll("[\u200E\u200F]", "");
//        questionEntity.setAnswer(answerWithoutLRM);
//
//        questionEntity = questionRepository.save(questionEntity);
//        BasicResponse basicResponse = new BasicResponse(true, null);
//       QuestionDTO questionDTO= questionEntityToQuestionDTOMapper.apply(questionEntity);
//       basicResponse.setData(questionDTO);
//        return basicResponse;
//    }



//    public BasicResponse generateQuestionForPractice(String userId, String subject, String topic, String subTopic) {
//        sendJobMessage(userId,subject,subTopic);
//
////        UserEntity user= userRepository.findUserByUserId(userId);
////        if(user == null) {
////            return new BasicResponse(false,"יוזר לא קיים");
////        }
////        if (!(user.getRole()== Role.STUDENT)){
////            return new BasicResponse(false,"יוזר זה אינו תלמיד");
////        }
////       StudentEntity student=(StudentEntity)user;
//
////        ClassRoomEntity classRoom=classRoomRepository.findClassRoomOfUserByUserId(userId);
//StudentEntity student =studentRepository.findFullStudent(userId);
//        if(student.getClassRoom() == null || student.getClassRoom().getId() == null){
//            return new BasicResponse(false,",תלמיד עוד לא משובץ לכיתה");
//        }
//
//        StudentProgressEntity studentProgressEntity = student.getStudentProgressEntity();
//
//
//        Map<String, Integer> skillLevelsBySubTopic = studentProgressEntity.getSkillLevelsBySubTopic();
//
//        skillLevelsBySubTopic.putIfAbsent(subTopic, 1);
//
//        studentProgressEntity.setSkillLevelsBySubTopic(skillLevelsBySubTopic);
//        studentProgressRepository.save(studentProgressEntity);
//
//        int level = skillLevelsBySubTopic.get(subTopic);
//        SubjectQuestionGenerator generator = QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);
//        QuestionGenerator questionGenerator = generator.getQuestionGenerator(topic, subTopic);
//
////        QuestionEntity baseQuestion = questionGenerator.generateQuestion(level);
//        StopWatch sw = new StopWatch();
//        sw.start();
//
//// קטע הקוד הכבד
//        QuestionEntity baseQuestion = questionGenerator.generateQuestion(level);
//        sw.stop();
//        System.out.println( sw.getTotalTimeMillis());
//
//        PracticeQuestionEntity questionEntity=questionEntityToPracticeQuestionMapper.apply(baseQuestion);
//
//        StudentQuestionHistoryEntity historyEntity = student.getQuestionHistoryEntity();
//
//        questionEntity.setQuestionHistory(historyEntity);
//        questionEntity.setProgressEntity(studentProgressEntity);
//
//        String questionTextWithoutLRM = questionEntity.getQuestionText().replaceAll("[\u200E\u200F]", "");
//        questionEntity.setQuestionText(questionTextWithoutLRM);
//
//        String answerWithoutLRM = questionEntity.getAnswer().replaceAll("[\u200E\u200F]", "");
//        questionEntity.setAnswer(answerWithoutLRM);
//
//        questionEntity = questionRepository.save(questionEntity);
//        BasicResponse basicResponse = new BasicResponse(true, null);
//        QuestionDTO questionDTO= questionEntityToQuestionDTOMapper.apply(questionEntity);
//        basicResponse.setData(questionDTO);
//        return basicResponse;
//    }




    @Transactional
    public BasicResponse generateQuestionForPractice(String userId, String subject, String topic, String subTopic) {
        sendJobMessage(userId, subject, subTopic);

        StopWatch totalStopWatch = new StopWatch();
        totalStopWatch.start();

        StopWatch sw = new StopWatch();

        // שלב 1: טעינת הסטודנט (רק עם progress ו-history, בלי cascades כבדים)
        sw.start();
        StudentEntity student = studentRepository.findStudentWithProgressAndHistory(userId);
        sw.stop();
        System.out.println("⏱️ load student time: " + sw.getTotalTimeMillis() + "ms");

        if (student.getClassRoom() == null || student.getClassRoom().getId() == null) {
            return new BasicResponse(false, "תלמיד עוד לא משובץ לכיתה");
        }

        // שלב 2: עדכון progress בזיכרון (ללא save)
        sw = new StopWatch();
        sw.start();
        StudentProgressEntity progress = student.getStudentProgressEntity();
        Map<String, Integer> skillLevels = progress.getSkillLevelsBySubTopic();
        skillLevels.putIfAbsent(subTopic, 1); // עדכון מקומי
        sw.stop();
        System.out.println("⏱️ update progress time: " + sw.getTotalTimeMillis() + "ms");

        // שלב 3: הפקת שאלה
        sw = new StopWatch();
        sw.start();
        int level = skillLevels.get(subTopic);
        SubjectQuestionGenerator generator = QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);
        QuestionGenerator questionGenerator = generator.getQuestionGenerator(topic, subTopic);
        QuestionEntity baseQuestion = questionGenerator.generateQuestion(level);
        sw.stop();
        System.out.println("⏱️ generate question logic time: " + sw.getTotalTimeMillis() + "ms");

        // שלב 4: יצירת הישויות לקשר לשאלה
        sw = new StopWatch();
        sw.start();
        PracticeQuestionEntity questionEntity = questionEntityToPracticeQuestionMapper.apply(baseQuestion);

        questionEntity.setProgressEntity(progress); // קשר LAZY
        questionEntity.setQuestionHistory(student.getStudentQuestionHistoryEntity()); // קשר LAZY

        // ניקוי תווי כיוון טקסט
        questionEntity.setQuestionText(questionEntity.getQuestionText().replaceAll("[\u200E\u200F]", ""));
        questionEntity.setAnswer(questionEntity.getAnswer().replaceAll("[\u200E\u200F]", ""));
        sw.stop();
        System.out.println("⏱️ prepare question entity time: " + sw.getTotalTimeMillis() + "ms");

        // שלב 5: שמירה לבסיס נתונים (עם Batch insert, אין save של progress/histories)
        sw = new StopWatch();
        sw.start();
        questionEntity = questionRepository.save(questionEntity);
        sw.stop();
        System.out.println("⏱️ save questionEntity time: " + sw.getTotalTimeMillis() + "ms");

        // שלב 6: מיפוי ל-DTO
        sw = new StopWatch();
        sw.start();
        QuestionDTO questionDTO = questionEntityToQuestionDTOMapper.apply(questionEntity);
        BasicResponse response = new BasicResponse(true, null);
        response.setData(questionDTO);
        sw.stop();
        System.out.println("⏱️ map to DTO time: " + sw.getTotalTimeMillis() + "ms");

        totalStopWatch.stop();
        System.out.println("✅ total time for generateQuestionForPractice: " + totalStopWatch.getTotalTimeMillis() + "ms");

        return response;
    }



    //TODO להשאיר רמה מקסימלית ל5
    @Transactional
    public BasicResponse submitAnswer(String userId, Long id, String subTopic, String answer) {

        UserEntity user= userRepository.findUserByUserId(userId);
        if(user == null) {
            return new BasicResponse(false,"יוזר לא קיים");
        }
        if (!(user.getRole()== Role.STUDENT)){
            return new BasicResponse(false,"יוזר זה אינו תלמיד");
        }

        ClassRoomEntity classRoom=classRoomRepository.findClassRoomOfUserByUserId(userId);
        if(classRoom == null){
            return new BasicResponse(false,",תלמיד עוד לא משובץ לכיתה");
        }
        StudentQuestionHistoryEntity historyEntity = studentQuestionHistoryRepository.findStudentQuestionHistoryByUserId(userId);
        StudentProgressEntity studentProgressEntity = studentProgressRepository.findStudentProgressByUserId(userId);


        QuestionEntity questionEntity = questionRepository.findQuestionById(id);
        if (questionEntity == null) {
            return new BasicResponse(false, "השאלה לא נמצאה");
        }
        boolean isCorrect = questionEntity.getAnswer().equalsIgnoreCase(answer.trim());
        System.out.println(questionEntity.getAnswer() + "  " + answer.trim());
        historyEntity.addAnsweredQuestion(questionEntity, isCorrect);
        studentQuestionHistoryRepository.save(historyEntity);

        Map<String, Integer> subTopicSuccessStreak = studentProgressEntity.getSubTopicSuccessStreak();
        int currentSuccesStreak = subTopicSuccessStreak.getOrDefault(subTopic, 0);
        Map<String, Integer> skillLevelsBySubTopic = studentProgressEntity.getSkillLevelsBySubTopic();;
        Map<String, Integer> subTopicIncorrectStreak = studentProgressEntity.getSubTopicIncorrectStreak();
        int currentIncorrectStreak = subTopicIncorrectStreak.getOrDefault(subTopic, 0);
        if (isCorrect) {
            subTopicIncorrectStreak.put(subTopic, 0);
            subTopicSuccessStreak.put(subTopic, currentSuccesStreak + 1);

            if (subTopicSuccessStreak.get(subTopic) == 3) {
                //skillLevelsBySubTopic = userProgressEntity.getSkillLevelsBySubTopic();
                skillLevelsBySubTopic.put(subTopic, skillLevelsBySubTopic.get(subTopic) + 1);
                subTopicSuccessStreak.put(subTopic, 0);
            }
        } else {

            subTopicSuccessStreak.put(subTopic, 0);
            subTopicIncorrectStreak.put(subTopic,currentIncorrectStreak+1 );
            if (subTopicIncorrectStreak.get(subTopic) == 3) {
                if(skillLevelsBySubTopic.get(subTopic)>1){
                    //skillLevelsBySubTopic = userProgressEntity.getSkillLevelsBySubTopic();
                    skillLevelsBySubTopic.put(subTopic, skillLevelsBySubTopic.get(subTopic) -1);
                    subTopicIncorrectStreak.put(subTopic, 0);
                }


            }
        }
        studentProgressEntity.setSkillLevelsBySubTopic(skillLevelsBySubTopic);
        studentProgressEntity.setSubTopicSuccessStreak(subTopicSuccessStreak);
        studentProgressEntity.setSubTopicIncorrectStreak(subTopicIncorrectStreak);
        studentProgressRepository.save(studentProgressEntity);

        SubmitAnswerResponse submitAnswerResponse=new SubmitAnswerResponse();
        submitAnswerResponse.setSuccess(true);
        System.out.println(isCorrect);
        submitAnswerResponse.setCorrect(isCorrect);
        submitAnswerResponse.setMessage(isCorrect ? "תשובה נכונה כל הכבוד" : "תשובה לא נכונה ");
        BasicResponse basicResponse = new BasicResponse(true, null);
        basicResponse.setData(submitAnswerResponse);
        return basicResponse;
    }



    @Transactional
    public BasicResponse generatePracticeTest(String userId, String subject, String topic, String difficulty, int questionCount, int timeLimitMinutes) {
        UserEntity user = userRepository.findUserByUserId(userId);
        if (user == null) {
            return new BasicResponse(false, "יוזר לא קיים");
        }

        if (!(user.getRole() == Role.STUDENT)) {
            return new BasicResponse(false, "יוזר זה אינו תלמיד");
        }

        ClassRoomEntity classRoom = classRoomRepository.findClassRoomOfUserByUserId(userId);
        if (classRoom == null) {
            return new BasicResponse(false, "תלמיד עוד לא משובץ לכיתה");
        }
//TODO לימצוא דרך איך ליבדוק את זה
//        List<String> subTopics = getSubTopics(topic);
//        if (subTopics.isEmpty()) {
//            return new BasicResponse(false, "לא נמצאו נושאים לתרגול בנושא זה");
//        }
//
//        int[] difficultyLevels = getDifficultyLevels(difficulty);
//        if (difficultyLevels.length == 0) {
//            return new BasicResponse(false, "קושי לא תקין");
//        }

        StudentEntity studentEntity = (StudentEntity) user;

        PracticeTestEntity testEntity = new PracticeTestEntity();

        testEntity.setStudent(studentEntity);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        testEntity.setStartTime(now.format(formatter));


        testEntity.setTimeLimitMinutes(timeLimitMinutes);
        testEntity.setSubject(subject);
        testEntity.setTopic(topic);
        testEntity.setDifficulty(difficulty);
        testEntity.setQuestionCount(questionCount);
        practiceTestRepository.save(testEntity);

        List<TestQuestionEntity> questions=testService.generateTestQuestions(subject,topic,questionCount,difficulty,testEntity);

        testEntity.setQuestions(questions);
        practiceTestRepository.save(testEntity);

        Map<TestEntity, List<TestQuestionEntity>> testMap = Map.of(testEntity, questions);
        TestDTO testDTO = testEntityToTestDTOMapper.apply(testMap);
        BasicResponse basicResponse = new BasicResponse(true,null);
        basicResponse.setData(testDTO);
        return basicResponse;
    }

}
