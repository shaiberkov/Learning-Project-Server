package org.example.learningprojectserver.service;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.entities.TestEntity;
import org.example.learningprojectserver.entities.TestQuestionEntity;
import org.example.learningprojectserver.mappers.QuestionEntityToTestQuestionMapper;
import org.example.learningprojectserver.mappers.TestEntityToTestDTOMapper;
import org.example.learningprojectserver.repository.QuestionRepository;
import org.example.learningprojectserver.repository.TestRepository;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGeneratorFactory;
import org.example.learningprojectserver.service.QuestionGenerator.SubjectQuestionGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final TestEntityToTestDTOMapper testEntityToTestDTOMapper;
    private final QuestionEntityToTestQuestionMapper questionEntityToTestQuestionMapper;
    private final QuestionRepository questionRepository;


    public TestDTO getTestDto(Long testId) {
    TestEntity testEntity = testRepository.findTestByTestId(testId);
    List<TestQuestionEntity> testQuestionEntities=testEntity.getQuestions();
        Map<TestEntity, List<TestQuestionEntity>> testEntityListMap =new HashMap<>();
        testEntityListMap.put(testEntity, testQuestionEntities);
    return testEntityToTestDTOMapper.apply(testEntityListMap);

    }


    public List<TestQuestionEntity> generateTestQuestions(
            String subject,
            String topic,
            int questionCount,
            String difficulty,
            TestEntity testEntity) {

        List<TestQuestionEntity> result = new ArrayList<>();

        Random random = new Random();
        int[] difficultyLevels = getDifficultyLevels(difficulty);
        List<String> subTopics = getSubTopics(topic);

        SubjectQuestionGenerator subjectGenerator =
                QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);

        for (int i = 0; i < questionCount; i++) {
            int level = difficultyLevels[random.nextInt(difficultyLevels.length)];
            String subTopic = subTopics.get(random.nextInt(subTopics.size()));

            QuestionGenerator questionGenerator =
                    subjectGenerator.getQuestionGenerator(topic, subTopic);

            QuestionEntity questionEntity = questionGenerator.generateQuestion(level);

            TestQuestionEntity tqe = questionEntityToTestQuestionMapper.apply(questionEntity);
            tqe.setTest(testEntity);
            result.add(tqe); // אל תשמור כאן עדיין
        }

        // שמירה מרוכזת אחרי הלולאה
        questionRepository.saveAll(result);

        return result;
    }


//    public List<TestQuestionEntity> generateTestQuestions(
//            String subject,
//            String topic,
//            int questionCount,
//            String difficulty,
//            TestEntity testEntity) {
//
//        List<TestQuestionEntity> result = new ArrayList<>();
//
//        for (int i = 0; i < questionCount; i++) {
//            result.add(createSingleQuestion(subject, topic, difficulty, testEntity));
//        }
//        return result;
//    }
//
//
//    private TestQuestionEntity createSingleQuestion(
//            String subject,
//            String topic,
//            String difficulty,
//            TestEntity testEntity) {
//
//        Random random = new Random();
//
//        int[] difficultyLevels = getDifficultyLevels(difficulty);
//        int level = difficultyLevels[random.nextInt(difficultyLevels.length)];
//
//        List<String> subTopics = getSubTopics(topic);
//        String subTopic = subTopics.get(random.nextInt(subTopics.size()));
//
//        SubjectQuestionGenerator subjectGenerator =
//                QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);
//
//        QuestionGenerator questionGenerator =
//                subjectGenerator.getQuestionGenerator(topic, subTopic);
//
//        QuestionEntity questionEntity = questionGenerator.generateQuestion(level);
//
//        TestQuestionEntity tqe = questionEntityToTestQuestionMapper.apply(questionEntity);
//        tqe.setTest(testEntity);
//        questionRepository.save(tqe);
//
//        return tqe;
//    }



    private static List<String> getSubTopics(String topic) {
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
