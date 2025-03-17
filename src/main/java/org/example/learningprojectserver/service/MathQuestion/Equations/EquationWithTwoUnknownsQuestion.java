package org.example.learningprojectserver.service.MathQuestion.Equations;


import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;

import java.util.Random;

public class EquationWithTwoUnknownsQuestion implements QuestionGenerator {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        int a1 = random.nextInt(max) + 1;
        int b1 = random.nextInt(max) + 1;
        int c1 = random.nextInt(max) + 1;

        int a2 = random.nextInt(max) + 1;
        int b2 = random.nextInt(max) + 1;
        int c2 = random.nextInt(max) + 1;

        boolean multiplyX1 = random.nextBoolean();
        boolean multiplyY1 = random.nextBoolean();
        boolean multiplyX2 = random.nextBoolean();
        boolean multiplyY2 = random.nextBoolean();

        if (multiplyX1) a1 *= random.nextInt(3) + 1;
        if (multiplyY1) b1 *= random.nextInt(3) + 1;
        if (multiplyX2) a2 *= random.nextInt(3) + 1;
        if (multiplyY2) b2 *= random.nextInt(3) + 1;

        int denominator = (a1 * b2 - a2 * b1);
        if (denominator == 0) {
            return generateQuestion(difficulty);
        }

        int x = (c1 * b2 - c2 * b1) / denominator;
        int y = (c1 - a1 * x) / b1;

        String questionText;
        boolean isXFirst = random.nextBoolean();

        if (isXFirst) {
            questionText = String.format("%dx + %dy = %d\n%dx + %dy = %d", a1, b1, c1, a2, b2, c2);
        } else {
            questionText = String.format("%dy + %dx = %d\n %dy + %dx = %d", b1, a1, c1, b2, a2, c2);
        }

        String answer = String.format("x = %d, y = %d", x, y);

        return new QuestionEntity("מתמטיקה","משוואות", "מערכת משוואות עם שני נעלמים", questionText, answer);
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