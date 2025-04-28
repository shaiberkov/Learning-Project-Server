package org.example.learningprojectserver.service.Tests;


import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.example.learningprojectserver.dto.QuestionDTO;
import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.repository.*;
import org.example.learningprojectserver.response.TestResultResponse;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGeneratorFactory;
import org.example.learningprojectserver.service.QuestionGenerator.SubjectQuestionGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TestService {

    private  TestResultRepository testResultRepository;
    private QuestionRepository questionRepository;
    private UserRepository UserRepository;
    private TestRepository testRepository;
    private TestResultRepository TestResultRepository;

    @Autowired
    public TestService(QuestionRepository questionRepository
            , UserRepository UserRepository, TestRepository testRepository, TestResultRepository testResultRepository) {
        this.questionRepository = questionRepository;
        this.UserRepository = UserRepository;
        this.testRepository = testRepository;
        this.TestResultRepository = testResultRepository;
        this.testResultRepository = testResultRepository;
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

    @Transactional
    public TestDTO generateTest(String userName, String subject, String topic, String difficulty, int questionCount) {
        UserEntity user = UserRepository.findByUserName(userName);

        Random random = new Random();
        SubjectQuestionGenerator generatorFactory = QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);

        List<QuestionEntity> questions = new ArrayList<>();
        List<String> subTopics = getSubTopics(topic);
        int[] difficultyLevels = getDifficultyLevels(difficulty);

        TestEntity testEntity = new TestEntity();
        testEntity.setUser(user);

        //todo הוספתי ליראות שנישמר
        testEntity.setSubject(subject);
        testEntity.setTopic(topic);
        testEntity.setDifficulty(difficulty);
        testEntity.setQuestionCount(questionCount);
        testRepository.save(testEntity);

        for (int i = 0; i < questionCount; i++) {
            int level = difficultyLevels[random.nextInt(difficultyLevels.length)];
            String subTopic = subTopics.get(random.nextInt(subTopics.size()));
            QuestionGenerator questionGenerator = generatorFactory.getQuestionGenerator(topic, subTopic);
            QuestionEntity questionEntity = questionGenerator.generateQuestion(level);

            questionEntity.setTest(testEntity);
            questionRepository.save(questionEntity);
            questions.add(questionEntity);
        }

        testEntity.setQuestions(questions);
        testRepository.save(testEntity);

        return mapToTestDto(testEntity,questions);
    }

    public TestResultEntity checkTest(String userName, Long testId, Map<Long, String> userAnswers) {

        UserEntity user = UserRepository.findByUserName(userName);
        TestEntity test = testRepository.findById(testId).orElse(null);

        List<QuestionEntity> correctQuestions = new ArrayList<>();
        List<QuestionEntity> incorrectQuestions = new ArrayList<>();
        int correctCount = 0;

        for (QuestionEntity question : test.getQuestions()) {
            String userAnswer = userAnswers.get(question.getId());
            if (userAnswer != null && userAnswer.equalsIgnoreCase(question.getAnswer().trim())) {
                correctQuestions.add(question);
                correctCount++;
            } else {
                incorrectQuestions.add(question);
            }
        }

        int score = (int) (((double) correctCount / test.getQuestions().size()) * 100);

        TestResultEntity testResult = new TestResultEntity();
        testResult.setUser(user);
        testResult.setTest(test);
        testResult.setScore(score);
        testResult.setCorrectAnswers(correctCount);
        testResult.setIncorrectAnswers(test.getQuestions().size() - correctCount);
        testResult.setCorrectQuestions(correctQuestions);
        testResult.setIncorrectQuestions(incorrectQuestions);

        testResultRepository.save(testResult);

        // עדכון UserEntity
        if (user.getUserTestsResult() == null) {
            user.setUserTestsResult(new ArrayList<>());
        }
        user.getUserTestsResult().add(testResult);
        UserRepository.save(user);

        return testResult;
    }
    public TestResultResponse getTestResultResponse(String userName, Long testId, Map<Long, String> userAnswers) {
        TestResultEntity testResultEntity = checkTest(userName, testId, userAnswers);

        if (testResultEntity == null) {
            return null;
        }

        TestResultResponse response = new TestResultResponse();
        response.setScore(testResultEntity.getScore());
        response.setCorrectAnswers(testResultEntity.getCorrectAnswers());
        response.setIncorrectAnswers(testResultEntity.getIncorrectAnswers());
        response.setCorrectAnswerList(testResultEntity.getCorrectQuestions().stream().map(QuestionEntity::getQuestionText).collect(Collectors.toList()));
        response.setIncorrectAnswerList(testResultEntity.getIncorrectQuestions().stream().map(QuestionEntity::getQuestionText).collect(Collectors.toList()));

        return response;
    }

    private TestDTO mapToTestDto(TestEntity testEntity,List<QuestionEntity> questions) {
        Long id=testEntity.getId();
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (QuestionEntity questionEntity : questions) {
            QuestionDTO questionDTO= mapToDTO(questionEntity);
            questionDTOs.add(questionDTO);
        }
        return new TestDTO(id,questionDTOs);
    }
    private QuestionDTO mapToDTO(QuestionEntity questionEntity) {
        return new QuestionDTO(
                questionEntity.getId(),
                questionEntity.getSubject(),
                questionEntity.getTopic(),
                questionEntity.getSubTopic(),
                questionEntity.getQuestionText()
        );
    }
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
}
