package org.example.learningprojectserver.service;


import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.MathQuestion.MathQuestion;
import org.example.learningprojectserver.service.MathQuestion.MathQuestionFactory;
import org.springframework.stereotype.Service;

@Service
public class MathQuestionService {

    public QuestionEntity generateMathQuestion( String subTopic, int difficulty) {


        MathQuestion questionGenerator = MathQuestionFactory.getMathQuestion(subTopic);
        return questionGenerator.generateQuestion(difficulty);
    }
}