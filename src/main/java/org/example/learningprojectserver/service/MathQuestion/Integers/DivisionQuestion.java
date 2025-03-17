package org.example.learningprojectserver.service.MathQuestion.Integers;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;

import java.util.Random;

public class DivisionQuestion implements QuestionGenerator {
    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        int divisor = random.nextInt(max - 1) + 1;
        int result = random.nextInt(max) + 1;
        int dividend = divisor * result;

        String questionText = String.format(" %d ÷ %d= ? ", dividend, divisor);
        String answer = String.valueOf(result);

        return new QuestionEntity("מתמטיקה","מספרים שלמים", "חילוק מספרים שלמים", questionText, answer);
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 5;
            case 2 -> 10;
            case 3 -> 15;
            case 4 -> 20;
            case 5 -> 25;
            default -> 25;
        };
    }
}