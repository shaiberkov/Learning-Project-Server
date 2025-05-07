package org.example.learningprojectserver.service;


import org.example.learningprojectserver.dto.QuestionDTO;
import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.repository.*;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.response.TestResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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










//@PostConstruct
//public void init() {
//        Map<Long,String> u=new HashMap<>();
//        u.put(25L,"8");
//        u.put(26L,"0");
//    System.out.println(checkPracticeTest("325256022", 13L,u));
//}

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


//    public TestResultResponse getTestResultResponse(String userName, Long testId, Map<Long, String> userAnswers) {
//        TestResultEntity testResultEntity = checkTest(userName, testId, userAnswers);
//
//        if (testResultEntity == null) {
//            return null;
//        }
//
//        TestResultResponse response = new TestResultResponse();
//        response.setScore(testResultEntity.getScore());
//        response.setCorrectAnswers(testResultEntity.getCorrectAnswers());
//        response.setIncorrectAnswers(testResultEntity.getIncorrectAnswers());
//        response.setCorrectAnswerList(testResultEntity.getCorrectQuestions().stream().map(QuestionEntity::getQuestionText).collect(Collectors.toList()));
//        response.setIncorrectAnswerList(testResultEntity.getIncorrectQuestions().stream().map(QuestionEntity::getQuestionText).collect(Collectors.toList()));
//
//        return response;
//    }


    // פונקציה שמחזירה את רשימת תתי הנושאים עבור נושא מסוים
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

    //todo לשים בקונטרולר וגם לשנות שממקבלים רשימת אי די של תלמידים
    public BasicResponse checkTeacherTest(String userId,String teacherId, Long testId, Map<Long, String> userAnswers) {

            UserEntity user1 = UserRepository.findUserByUserId(userId);
            UserEntity user2 = UserRepository.findUserByUserId(teacherId);


            if (user1.getRole() != Role.STUDENT) {
                return new BasicResponse(false, "אין הרשאה ליצירת מיבחנים לתירגול אישי");
            }

            if (user2.getRole() != Role.TEACHER) {
                return new BasicResponse(false, "אין לו מורה ");
            }

            StudentEntity student = (StudentEntity) user1;
            TeacherEntity teacher = (TeacherEntity) user2;
            TeacherTestEntity test = teacherTestRepository.findById(testId).orElse(null);

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

            TeacherTestResultEntity testResult = new  TeacherTestResultEntity();
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
