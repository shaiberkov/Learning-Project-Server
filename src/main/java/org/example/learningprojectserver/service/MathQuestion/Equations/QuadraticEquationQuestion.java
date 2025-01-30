package org.example.learningprojectserver.service.MathQuestion.Equations;


import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator;

import java.util.Random;

public class QuadraticEquationQuestion implements QuestionGenerator {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        int a = random.nextInt(max) + 1;
        int b = random.nextInt(max) + 1;
        int c = random.nextInt(max) + 1;

        int discriminant = b * b - 4 * a * c;
        while (!isPerfectSquare(discriminant)) {
            c = random.nextInt(max) + 1;
            discriminant = b * b - 4 * a * c;
        }

        String questionText = String.format("%dx² + %dx + %d = 0", a, b, c);
        double sqrtDiscriminant = Math.sqrt(discriminant);
        int root1 = (-b + (int) sqrtDiscriminant) / (2 * a);
        int root2 = (-b - (int) sqrtDiscriminant) / (2 * a);

        String answer = String.format("x1 = %d, x2 = %d", root1, root2);

        return new QuestionEntity("מתמטיקה","משוואות", "משוואה ריבועית", questionText, answer);
    }

    private boolean isPerfectSquare(int num) {
        int sqrt = (int) Math.sqrt(num);
        return sqrt * sqrt == num;
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 5;
            case 2 -> 10;
            case 3 -> 20;
            case 4 -> 30;
            case 5 -> 50;
            default -> throw new IllegalArgumentException("דרגת קושי לא תקינה");
        };
    }
}