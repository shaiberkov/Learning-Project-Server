package org.example.learningprojectserver.service;

import org.example.learningprojectserver.entities.QuestionEntity;

public interface SubjectQuestionGenerator {
    QuestionGenerator getQuestionGenerator(String topic, String subTopic);
}
