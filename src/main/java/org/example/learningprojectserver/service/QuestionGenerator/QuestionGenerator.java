package org.example.learningprojectserver.service.QuestionGenerator;

import org.example.learningprojectserver.entities.QuestionEntity;

public interface QuestionGenerator {
    QuestionEntity generateQuestion(int difficulty);
}
