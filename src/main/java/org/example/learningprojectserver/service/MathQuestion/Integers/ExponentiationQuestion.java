package org.example.learningprojectserver.service.MathQuestion.Integers;


import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;

import java.util.Random;

public class ExponentiationQuestion implements QuestionGenerator {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);


        int base = random.nextInt(max) + 1;
        int exponent = random.nextInt(3) + 1;

        String questionText = buildQuestionText(base, exponent);
        String answer = String.valueOf(calculateExponentiation(base, exponent));

        return new QuestionEntity("מתמטיקה", "מספרים שלמים","חזקות", questionText, answer);
    }

    private String buildQuestionText(int base, int exponent) {
        return String.format("%d^%d = ?", base, exponent);
    }

    private int calculateExponentiation(int base, int exponent) {
        return (int) Math.pow(base, exponent);
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 5;
            case 2 -> 7;
            case 3 -> 9;
            case 4 -> 11;
            case 5 -> 13;
            default -> 13;
        };
    }
}