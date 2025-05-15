package org.example.learningprojectserver.service;

import jakarta.transaction.Transactional;
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
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
public class StudentService {

private final StudentProgressRepository studentProgressRepository;
private final StudentQuestionHistoryRepository studentQuestionHistoryRepository;
private final QuestionRepository questionRepository;
private final UserRepository userRepository;
private final PracticeTestRepository practiceTestRepository;
private final ClassRoomRepository classRoomRepository;
private final QuestionEntityToTestQuestionMapper questionEntityToTestQuestionMapper;
private final TestEntityToTestDTOMapper testEntityToTestDTOMapper;
private final QuestionEntityToQuestionDTOMapper questionEntityToQuestionDTOMapper;
private final QuestionEntityToPracticeQuestionMapper questionEntityToPracticeQuestionMapper;
private final SchoolRepository schoolRepository;
private final LessonsToScheduleMapper lessonsToScheduleMapper;
private final ChatGptService chatGptService;
private final StudentEntityToStudentTestStatusDTOMapper studentEntityToStudentTestStatusDTOMapper;


@Autowired
    public StudentService(StudentProgressRepository studentProgressRepository, StudentQuestionHistoryRepository studentQuestionHistoryRepository, QuestionRepository questionRepository, UserRepository userRepository, PracticeTestRepository practiceTestRepository, ClassRoomRepository classRoomRepository, QuestionEntityToTestQuestionMapper questionEntityToTestQuestionMapper, QuestionEntityToQuestionDTOMapper questionEntityToQuestionDTOMapper, TestEntityToTestDTOMapper testEntityToTestDTOMapper, QuestionEntityToQuestionDTOMapper questionEntityToQuestionDTOMapper1, QuestionEntityToPracticeQuestionMapper questionEntityToPracticeQuestionMapper, SchoolRepository schoolRepository, LessonsToScheduleMapper lessonsToScheduleMapper, ChatGptService chatGptService, StudentEntityToStudentTestStatusDTOMapper studentEntityToStudentTestStatusDTOMapper) {
        this.studentProgressRepository = studentProgressRepository;
        this.studentQuestionHistoryRepository = studentQuestionHistoryRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.practiceTestRepository = practiceTestRepository;
    this.classRoomRepository = classRoomRepository;
    this.questionEntityToTestQuestionMapper = questionEntityToTestQuestionMapper;
    this.testEntityToTestDTOMapper = testEntityToTestDTOMapper;
    this.questionEntityToQuestionDTOMapper = questionEntityToQuestionDTOMapper1;
    this.questionEntityToPracticeQuestionMapper = questionEntityToPracticeQuestionMapper;
    this.schoolRepository = schoolRepository;
    this.lessonsToScheduleMapper = lessonsToScheduleMapper;
    this.chatGptService = chatGptService;
    this.studentEntityToStudentTestStatusDTOMapper = studentEntityToStudentTestStatusDTOMapper;
}

//@PostConstruct
//public void init() {
//    System.out.println(getStudentTestsStatus("325256017"));
//}

    public List<StudentTestStatusDTO> getStudentTestsStatus(String userId) {
        UserEntity user= userRepository.findUserByUserId(userId);
        if (!(user.getRole()== Role.STUDENT)){
            return new ArrayList();
        }
        StudentEntity student = (StudentEntity) user;

        return studentEntityToStudentTestStatusDTOMapper.apply(student);

    }

public BasicResponse getStudentSchedule(String schoolCode, String studentId){

    SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
    if (school == null) {
        return new BasicResponse(false, "בית הספר לא נמצא לפי הקוד שסופק");
    }

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


//    @PostConstruct
//    public void init() {
//        System.out.println(generateQuestionForPractice("325256022","מתמטיקה","מספרים שלמים","חיבור מספרים שלמים"));
//        System.out.println(submitAnswer("325256022", 1L,"מתמטיקה","מספרים שלמים","חיבור מספרים שלמים","6+7","1"));
//    }
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

    public BasicResponse generateQuestionForPractice(String userId, String subject, String topic, String subTopic) {
        sendJobMessage(userId,subject,subTopic);

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

        StudentProgressEntity studentProgressEntity = studentProgressRepository.findStudentProgressByUserId(userId);
        System.out.println(studentProgressEntity);
        if (studentProgressEntity == null) {
            //throw new RuntimeException("User progress record not found for user: " + userName);
        }

        Map<String, Integer> skillLevelsBySubTopic = studentProgressEntity.getSkillLevelsBySubTopic();
        System.out.println(skillLevelsBySubTopic);

        // אם המשתמש אף פעם לא ענה על שאלה מהסוג הזה, נוסיף אותו עם רמה 1
        skillLevelsBySubTopic.putIfAbsent(subTopic, 1);

        // עדכון הנתונים ושמירתם
        studentProgressEntity.setSkillLevelsBySubTopic(skillLevelsBySubTopic);
        studentProgressRepository.save(studentProgressEntity);

        // קבלת הרמה של המשתמש
        int level = skillLevelsBySubTopic.get(subTopic);
        // יצירת שאלה באמצעות מחולל השאלות המתאים
        SubjectQuestionGenerator generator = QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);
        QuestionGenerator questionGenerator = generator.getQuestionGenerator(topic, subTopic);

        QuestionEntity baseQuestion = questionGenerator.generateQuestion(level);

        PracticeQuestionEntity questionEntity=questionEntityToPracticeQuestionMapper.apply(baseQuestion);

        // שליפת היסטוריית השאלות של המשתמש
        StudentQuestionHistoryEntity historyEntity = studentQuestionHistoryRepository.findStudentQuestionHistoryByUserId(userId);

        // **עדכון המפתחות הזרים** לפני שמירת השאלה
        questionEntity.setQuestionHistory(historyEntity);
        questionEntity.setProgressEntity(studentProgressEntity);

        // סינון תווי LRM מטקסט השאלה לפני שמירה
        String questionTextWithoutLRM = questionEntity.getQuestionText().replaceAll("[\u200E\u200F]", "");
        questionEntity.setQuestionText(questionTextWithoutLRM);

        // סינון תווי LRM מהתשובה לפני שמירה
        String answerWithoutLRM = questionEntity.getAnswer().replaceAll("[\u200E\u200F]", "");
        questionEntity.setAnswer(answerWithoutLRM);

        // שמירת השאלה במסד הנתונים
        questionEntity = questionRepository.save(questionEntity);
        BasicResponse basicResponse = new BasicResponse(true, null);
       QuestionDTO questionDTO= questionEntityToQuestionDTOMapper.apply(questionEntity);
       basicResponse.setData(questionDTO);
        return basicResponse;
    }


    //TODO להשאיר רמה מקסימלית ל5
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
        // חיפוש היסטוריית השאלות של המשתמש
        StudentQuestionHistoryEntity historyEntity = studentQuestionHistoryRepository.findStudentQuestionHistoryByUserId(userId);
        // חיפוש נתוני התקדמות של המשתמש
        StudentProgressEntity studentProgressEntity = studentProgressRepository.findStudentProgressByUserId(userId);


        QuestionEntity questionEntity = questionRepository.findQuestionById(id);
        if (questionEntity == null) {
            return new BasicResponse(false, "השאלה לא נמצאה");
        }
        // בדיקת נכונות התשובה
        boolean isCorrect = questionEntity.getAnswer().equalsIgnoreCase(answer.trim());
        System.out.println(questionEntity.getAnswer() + "  " + answer.trim());
        // עדכון היסטוריית השאלות של המשתמש
        historyEntity.addAnsweredQuestion(questionEntity, isCorrect);
        studentQuestionHistoryRepository.save(historyEntity); // שמירת היסטוריית השאלות

        // עדכון רצף ההצלחה של המשתמש בנושא ספציפי
        Map<String, Integer> subTopicSuccessStreak = studentProgressEntity.getSubTopicSuccessStreak();
        int currentSuccesStreak = subTopicSuccessStreak.getOrDefault(subTopic, 0);
        // עדכון אם התשובה נכונה או לא
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
        // שמירה של התקדמות המשתמש
        studentProgressEntity.setSubTopicSuccessStreak(subTopicSuccessStreak);
        studentProgressEntity.setSubTopicIncorrectStreak(subTopicIncorrectStreak);
        studentProgressRepository.save(studentProgressEntity);

        // החזרת תשובה ללקוח(true,isCorrect, isCorrect ? "תשובה נכונה" : "Incorrect answer.");
        SubmitAnswerResponse submitAnswerResponse=new SubmitAnswerResponse();
        submitAnswerResponse.setSuccess(true);
        System.out.println(isCorrect);
        submitAnswerResponse.setCorrect(isCorrect);
        submitAnswerResponse.setMessage(isCorrect ? "תשובה נכונה כל הכבוד" : "תשובה לא נכונה ");
        BasicResponse basicResponse = new BasicResponse(true, null);
        basicResponse.setData(submitAnswerResponse);
        return basicResponse;
    }

//    @PostConstruct
//    public void init() {
//
//        System.out.println(generatePracticeTest("325256022","מתמטיקה","מספרים שלמים","קל",2,30));
//
//
//    }

    @Transactional
    public BasicResponse generatePracticeTest(String userId, String subject, String topic, String difficulty, int questionCount, int timeLimitMinutes) {
        UserEntity user = userRepository.findUserByUserId(userId);
        if (user == null) {
            return new BasicResponse(false, "יוזר לא קיים");
        }

        // בדיקה אם המשתמש הוא תלמיד
        if (!(user.getRole() == Role.STUDENT)) {
            return new BasicResponse(false, "יוזר זה אינו תלמיד");
        }

//        // חיפוש כיתה שבה המשתמש משובץ
//        ClassRoomEntity classRoom = classRoomRepository.findClassRoomOfUserByUserId(userId);
//        if (classRoom == null) {
//            return new BasicResponse(false, "תלמיד עוד לא משובץ לכיתה");
//        }

        // בדיקה אם נושא או קושי נתונים נכונים
        List<String> subTopics = getSubTopics(topic);
        if (subTopics.isEmpty()) {
            return new BasicResponse(false, "לא נמצאו נושאים לתרגול בנושא זה");
        }

        int[] difficultyLevels = getDifficultyLevels(difficulty);
        if (difficultyLevels.length == 0) {
            return new BasicResponse(false, "קושי לא תקין");
        }

        StudentEntity studentEntity = (StudentEntity) user;
        Random random = new Random();
        SubjectQuestionGenerator generatorFactory = QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);

        List<TestQuestionEntity> questions = new ArrayList<>();

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

        for (int i = 0; i < questionCount; i++) {
            int level = difficultyLevels[random.nextInt(difficultyLevels.length)];
            String subTopic = subTopics.get(random.nextInt(subTopics.size()));
            QuestionGenerator questionGenerator = generatorFactory.getQuestionGenerator(topic, subTopic);
            QuestionEntity questionEntity = questionGenerator.generateQuestion(level);

            TestQuestionEntity testQuestionEntity= questionEntityToTestQuestionMapper.apply(questionEntity);
            testQuestionEntity.setTest(testEntity);
            questionRepository.save(testQuestionEntity);
            questions.add(testQuestionEntity);
        }

        testEntity.setQuestions(questions);
        practiceTestRepository.save(testEntity);

        Map<TestEntity, List<TestQuestionEntity>> testMap = Map.of(testEntity, questions);
        TestDTO testDTO = testEntityToTestDTOMapper.apply(testMap);
        BasicResponse basicResponse = new BasicResponse(true,null);
         basicResponse.setData(testDTO);
         return basicResponse;
    }



    public static List<String> getSubTopics(String topic) {
        switch (topic.toLowerCase()) {
            case "מספרים שלמים":
                return List.of(
                        "חיבור מספרים שלמים",
                        "חיסור מספרים שלמים",
                        "כפל מספרים שלמים",
                        "חילוק מספרים שלמים"
                );
            default:
                throw new IllegalArgumentException("נושא לא נתמך: ");
        }
    }

    private static int[] getDifficultyLevels(String difficulty) {
        return switch (difficulty.toLowerCase()) {
            case "קל" -> new int[]{1, 2};
            case "בינוני" -> new int[]{3, 4};
            case "קשה" -> new int[]{4, 5};
            default -> throw new IllegalArgumentException("רמת קושי לא נתמכת: " + difficulty);
        };
    }
}
