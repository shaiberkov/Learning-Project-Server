package org.example.learningprojectserver.service.MathQuestion.Integers;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;

import java.util.Random;

public class SubtractionQuestion implements QuestionGenerator {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        int numCount = (difficulty >= 3 && difficulty <= 5) ? 3 : 2;

        int[] numbers = new int[numCount];
        for (int i = 0; i < numCount; i++) {
            numbers[i] = random.nextInt(max) + 1;
        }


        int minuend = Math.max(numbers[0], numbers[1]);
        int subtrahend = Math.min(numbers[0], numbers[1]);
        int difference = minuend - subtrahend;


        String questionText = buildQuestionText(minuend, subtrahend);
        String answer = String.valueOf(difference);

        return new QuestionEntity("מתמטיקה","מספרים שלמים", "חיסור מספרים שלמים", questionText, answer);
    }

    private String buildQuestionText(int minuend, int subtrahend) {
        return String.format("%d - %d=?", minuend, subtrahend);
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 10;
            case 2 -> 20;
            case 3 -> 50;
            case 4 -> 100;
            case 5 -> 200;
            default -> 200;
        };
    }
}
