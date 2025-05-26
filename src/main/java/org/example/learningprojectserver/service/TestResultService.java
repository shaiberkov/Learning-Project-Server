package org.example.learningprojectserver.service;


import jakarta.annotation.PostConstruct;
import org.example.learningprojectserver.dto.QuestionDTO;
import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.repository.*;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.TestResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestResultService {

    private  TestResultRepository testResultRepository;
    private QuestionRepository questionRepository;
    private UserRepository UserRepository;
    private TestRepository testRepository;
    private TestResultRepository TestResultRepository;
    private final PracticeTestResultRepository practiceTestResultRepository;
    private final PracticeTestRepository practiceTestRepository;
    private final TeacherTestResultRepository teacherTestResultRepository;
    private final TeacherTestRepository teacherTestRepository;


    @Autowired
    public TestResultService(QuestionRepository questionRepository
            , UserRepository UserRepository, TestRepository testRepository, TestResultRepository testResultRepository, PracticeTestResultRepository practiceTestResultRepository, PracticeTestRepository practiceTestRepository, TeacherTestResultRepository teacherTestResultRepository, TeacherTestRepository teacherTestRepository) {
        this.questionRepository = questionRepository;
        this.UserRepository = UserRepository;
        this.testRepository = testRepository;
        this.TestResultRepository = testResultRepository;
        this.testResultRepository = testResultRepository;
        this.practiceTestResultRepository = practiceTestResultRepository;
        this.practiceTestRepository = practiceTestRepository;
        this.teacherTestResultRepository = teacherTestResultRepository;
        this.teacherTestRepository = teacherTestRepository;
    }


//    @PostConstruct
//    public void init() {
//
//       System.out.println(generateTest("shai","מתמטיקה","מספרים שלמים","קשה",5));
//
//
//    }


//    private String subject;
//    private String difficulty;
//    private int questionCount;













    public BasicResponse startTest(Long testId,String userId) {
        TeacherTestEntity testEntity = teacherTestRepository.findTeacherTestByTestId(testId);

        if (testEntity == null) {
            return new BasicResponse(false, "מבחן לא קיים");
        }


        String testStartTime = testEntity.getStartTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime testStartDateTime = LocalDateTime.parse(testStartTime, formatter);
        LocalDateTime now = LocalDateTime.now();

        if (!now.toLocalDate().isEqual(testStartDateTime.toLocalDate())) {
            return new BasicResponse(false, "התאריך הנוכחי לא תואם לתאריך המבחן");
        }


        if (now.isBefore(testStartDateTime)) {
            return new BasicResponse(false, "הזמן טרם הגיע להתחלת המבחן");
        }

        if (now.isAfter(testStartDateTime.plusMinutes(15))) {
            Map<Long,String> userAnswers= new HashMap<>();
            for (QuestionEntity questionEntity : testEntity.getQuestions()) {
                userAnswers.put(questionEntity.getId(),"");
            }
                checkTeacherTest(userId,testId,userAnswers);
            return new BasicResponse(false, "המבחן לא יכול להתחיל אחרי 15 דקות מתחילת הזמן לכן ציון 0");
        }
        return new BasicResponse(true, "המבחן התחיל בהצלחה");
    }


    public BasicResponse checkPracticeTest(String userid, Long testId, Map<Long, String> userAnswers) {


        UserEntity user = UserRepository.findUserByUserId(userid);

        if (user.getRole() != Role.STUDENT) {
            return new BasicResponse(false, "אין הרשאה לפעולה זו");

        }
        StudentEntity student = (StudentEntity) user;
        PracticeTestEntity test = practiceTestRepository.findById(testId).orElse(null);



            List<TestQuestionEntity> correctQuestions = new ArrayList<>();
        List<TestQuestionEntity> incorrectQuestions = new ArrayList<>();
        int correctCount = 0;

        for (TestQuestionEntity question : test.getQuestions()) {
            String userAnswer = userAnswers.get(question.getId());
            if (userAnswer != null && userAnswer.equalsIgnoreCase(question.getAnswer().trim())) {
                correctQuestions.add(question);
                correctCount++;
            } else {
                incorrectQuestions.add(question);
            }
        }

        int score = (int) (((double) correctCount / test.getQuestions().size()) * 100);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime now = LocalDateTime.now();
        PracticeTestResultEntity testResult = new PracticeTestResultEntity();
        testResult.setStudent(student);
        testResult.setTest(test);
        testResult.setScore(score);
        testResult.setCorrectAnswersCount(correctCount);
        testResult.setIncorrectAnswersCount(test.getQuestions().size() - correctCount);
        testResult.setCorrectQuestions(correctQuestions);
        testResult.setIncorrectQuestions(incorrectQuestions);
        testResult.setFinishTime(now.format(formatter));


        practiceTestResultRepository.save(testResult);



        student.getPracticeTestsResult().add(testResult);
        UserRepository.save(student);

        TestResultResponse response = new TestResultResponse();
        response.setScore(score);
        response.setCorrectAnswers(correctCount);
        response.setIncorrectAnswers(test.getQuestions().size() - correctCount);

        List<String> correctTexts = correctQuestions.stream()
                .map(QuestionEntity::getQuestionText)
                .toList();

        List<String> incorrectTexts = incorrectQuestions.stream()
                .map(QuestionEntity::getQuestionText)
                .toList();

        response.setCorrectAnswerList(correctTexts);
        response.setIncorrectAnswerList(incorrectTexts);

        BasicResponse basicResponse = new BasicResponse(true, null);
        basicResponse.setData(response);
            return basicResponse;

    }


    @CacheEvict(value = "studentTestsStatus", key = "#userId")
    public BasicResponse checkTeacherTest(String userId, Long testId, Map<Long, String> userAnswers) {

            UserEntity user1 = UserRepository.findUserByUserId(userId);

        if (user1.getRole() != Role.STUDENT) {
            return new BasicResponse(false, "אין הרשאה ליצירת מיבחנים לתירגול אישי");
        }
        TeacherTestEntity test = teacherTestRepository.findById(testId).orElse(null);
        if (test == null) {
            return new BasicResponse(false,"לא קיים מיבחן כזה");
        }
        TeacherEntity teacher =test.getTeacher();

            StudentEntity student = (StudentEntity) user1;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime now = LocalDateTime.now();



            List<TestQuestionEntity> correctQuestions = new ArrayList<>();
            List<TestQuestionEntity> incorrectQuestions = new ArrayList<>();
            int correctCount = 0;

            for (TestQuestionEntity question : test.getQuestions()) {
                String userAnswer = userAnswers.get(question.getId());
                if (userAnswer != null && userAnswer.equalsIgnoreCase(question.getAnswer().trim())) {
                    correctQuestions.add(question);
                    correctCount++;
                } else {
                    incorrectQuestions.add(question);
                }
            }

            int score = (int) (((double) correctCount / test.getQuestions().size()) * 100);

            TeacherTestResultEntity testResult = new TeacherTestResultEntity();
            testResult.setStudent(student);
            testResult.setTeacher(teacher);
            testResult.setTest(test);
            testResult.setScore(score);
            testResult.setCorrectAnswersCount(correctCount);
            testResult.setIncorrectAnswersCount(test.getQuestions().size() - correctCount);
            testResult.setCorrectQuestions(correctQuestions);
            testResult.setIncorrectQuestions(incorrectQuestions);
            testResult.setFinishTime(now.format(formatter));
            teacherTestResultRepository.save(testResult);


            student.getTeacherTestsResult().add(testResult);
            UserRepository.save(student);

            TestResultResponse response = new TestResultResponse();
            response.setScore(score);
            response.setCorrectAnswers(correctCount);
            response.setIncorrectAnswers(test.getQuestions().size() - correctCount);

            List<String> correctTexts = correctQuestions.stream()
                    .map(QuestionEntity::getQuestionText)
                    .toList();

            List<String> incorrectTexts = incorrectQuestions.stream()
                    .map(QuestionEntity::getQuestionText)
                    .toList();

            response.setCorrectAnswerList(correctTexts);
            response.setIncorrectAnswerList(incorrectTexts);

            BasicResponse basicResponse = new BasicResponse(true, null);
            basicResponse.setData(response);
            return basicResponse;

    }


}
