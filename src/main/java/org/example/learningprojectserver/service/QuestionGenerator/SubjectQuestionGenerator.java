package org.example.learningprojectserver.service.QuestionGenerator;

public interface SubjectQuestionGenerator {
    QuestionGenerator getQuestionGenerator(String topic, String subTopic);
}
