package org.example.learningprojectserver.service;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    public QuestionEntity generateQuestion(String subject, String topic, String subTopic, int difficulty) {
        SubjectQuestionGenerator generator = QuestionGeneratorFactory.getSubjectQuestionGenerator(subject);
        QuestionGenerator questionGenerator = generator.getQuestionGenerator(topic, subTopic);
        return questionGenerator.generateQuestion(difficulty);
    }
}
