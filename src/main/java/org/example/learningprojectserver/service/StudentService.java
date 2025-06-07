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
import java.util.*;
import java.util.stream.Collectors;


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
private final LessonRepository lessonRepository;



@Cacheable(value = "studentTestsStatus", key = "#userId")
public List<StudentTestStatusDTO> getStudentTestsStatus(String userId) {

      return studentRepository.findAllTestsForStudent(userId);
}

    @Cacheable(value = "studentSchedule", key = "#studentId")
    public BasicResponse getStudentSchedule(String schoolCode, String studentId) {
        List<LessonDTO> lessons = lessonRepository.findLessonsByStudentId(studentId);

        Map<DayOfWeek, List<LessonDTO>> lessonsByDay = lessons.stream()
                .collect(Collectors.groupingBy(
                        LessonDTO::getDayOfWeek,
                        () -> new TreeMap<>(Comparator.comparingInt(day -> day == DayOfWeek.SUNDAY ? 0 : day.getValue())),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(LessonDTO::getStartTime))
                                        .collect(Collectors.toList())
                        )
                ));

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

    @Transactional
    public BasicResponse generateQuestionForPractice(String userId, String subject, String topic, String subTopic) {
        sendJobMessage(userId, subject, subTopic);

        StudentEntity student = studentRepository.findStudentWithProgressAndHistory(userId);

        if (student.getClassRoom() == null || student.getClassRoom().getId() == null) {
            return new BasicResponse(false, "תלמיד עוד לא משובץ לכיתה");
        }

        StudentProgressEntity progress = student.getStudentProgressEntity();
        Map<String, Integer> skillLevels = progress.getSkillLevelsBySubTopic();
        skillLevels.putIfAbsent(subTopic, 1);


        int level = skillLevels.get(subTopic);
        SubjectQuestionGenerator generator = QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);
        QuestionGenerator questionGenerator = generator.getQuestionGenerator(topic, subTopic);
        QuestionEntity baseQuestion = questionGenerator.generateQuestion(level);

        PracticeQuestionEntity questionEntity = questionEntityToPracticeQuestionMapper.apply(baseQuestion);

        questionEntity.setProgressEntity(progress);
        questionEntity.setQuestionHistory(student.getStudentQuestionHistoryEntity());
        questionEntity.setQuestionText(questionEntity.getQuestionText().replaceAll("[\u200E\u200F]", ""));
        questionEntity.setAnswer(questionEntity.getAnswer().replaceAll("[\u200E\u200F]", ""));


        questionEntity = questionRepository.save(questionEntity);

        QuestionDTO questionDTO = questionEntityToQuestionDTOMapper.apply(questionEntity);
        BasicResponse response = new BasicResponse(true, null);
        response.setData(questionDTO);
        return response;
    }



    //TODO להשאיר רמה מקסימלית ל5
    @Transactional
    public BasicResponse submitAnswer(String userId, Long id, String subTopic, String answer) {

        StudentEntity student = studentRepository.findStudentWithAllData(userId);
        if(student == null) {
            return new BasicResponse(false,"יוזר לא קיים");
        }


        if(student.getClassRoom() == null){
            return new BasicResponse(false,",תלמיד עוד לא משובץ לכיתה");
        }
        StudentQuestionHistoryEntity historyEntity = student.getQuestionHistoryEntity();
        StudentProgressEntity studentProgressEntity = student.getStudentProgressEntity();


        QuestionEntity questionEntity = questionRepository.findQuestionById(id);
        if (questionEntity == null) {
            return new BasicResponse(false, "השאלה לא נמצאה");
        }
        boolean isCorrect = questionEntity.getAnswer().equalsIgnoreCase(answer.trim());
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


        StudentEntity student=studentRepository.findStudentWithClassRoomByUserId(userId);
        if (student == null) {
            return new BasicResponse(false, "יוזר לא קיים");
        }


        if (student.getClassRoom() == null) {
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


        PracticeTestEntity testEntity = new PracticeTestEntity();

        testEntity.setStudent(student);

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
