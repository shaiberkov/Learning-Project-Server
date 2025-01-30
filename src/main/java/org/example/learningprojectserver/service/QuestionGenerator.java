package org.example.learningprojectserver.service;

import org.example.learningprojectserver.entities.QuestionEntity;

public interface QuestionGenerator {
    QuestionEntity generateQuestion(int difficulty);
}
