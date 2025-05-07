package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.entities.PracticeQuestionEntity;
import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.entities.TestQuestionEntity;
import org.springframework.stereotype.Component;

@Component
public class QuestionEntityToPracticeQuestionMapper implements Mapper<QuestionEntity, PracticeQuestionEntity>{
    @Override
    public PracticeQuestionEntity apply(QuestionEntity baseQuestion) {
        PracticeQuestionEntity questionEntity = new PracticeQuestionEntity();
        questionEntity.setSubject(baseQuestion.getSubject());
        questionEntity.setTopic(baseQuestion.getTopic());
        questionEntity.setSubTopic(baseQuestion.getSubTopic());
        questionEntity.setQuestionText(baseQuestion.getQuestionText());
        questionEntity.setAnswer(baseQuestion.getAnswer());
        return questionEntity;
    }
}
