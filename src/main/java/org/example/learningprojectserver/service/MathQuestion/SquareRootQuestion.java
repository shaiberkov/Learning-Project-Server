package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class SquareRootQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);


        int number = random.nextInt(max) + 1;

        int perfectSquare = number * number;

        String questionText = buildQuestionText(perfectSquare);
        String answer = String.valueOf(number);

        return new QuestionEntity("מתמטיקה", "שורש ריבועי", questionText, answer);
    }

    private String buildQuestionText(int perfectSquare) {
        return String.format("√%d = ?", perfectSquare);
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 5;
            case 2 -> 10;
            case 3 -> 15;
            case 4 -> 20;
            case 5 -> 25;
            default -> throw new IllegalArgumentException("דרגת קושי לא תקינה");
        };
    }
}