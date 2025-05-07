package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.entities.TestQuestionEntity;
import org.springframework.stereotype.Component;

@Component
public class QuestionEntityToTestQuestionMapper implements Mapper<QuestionEntity, TestQuestionEntity> {
    @Override
    public TestQuestionEntity apply(QuestionEntity question) {
        TestQuestionEntity testQuestionEntity = new TestQuestionEntity();
        testQuestionEntity.setId(question.getId());
        testQuestionEntity.setSubject(question.getSubject());
        testQuestionEntity.setTopic(question.getTopic());
        testQuestionEntity.setSubTopic(question.getSubTopic());
        testQuestionEntity.setQuestionText(question.getQuestionText());
        testQuestionEntity.setAnswer(question.getAnswer());


    return testQuestionEntity;

    }

}
