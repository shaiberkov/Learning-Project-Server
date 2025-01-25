package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

public interface MathQuestion {
    QuestionEntity generateQuestion(int difficulty);
}
