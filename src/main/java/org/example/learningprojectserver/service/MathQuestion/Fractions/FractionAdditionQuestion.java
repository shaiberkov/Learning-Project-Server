package org.example.learningprojectserver.service.MathQuestion.Fractions;


import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;

import java.util.Random;

public class FractionAdditionQuestion implements QuestionGenerator {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        int numerator1 = random.nextInt(max) + 1;
        int denominator1 = random.nextInt(max - 1) + 2;
        int numerator2 = random.nextInt(max) + 1;
        int denominator2 = random.nextInt(max - 1) + 2;

        int numerator3 = 0;
        int denominator3 = 0;
        if (difficulty >= 3) {
            numerator3 = random.nextInt(max) + 1;
            denominator3 = random.nextInt(max - 1) + 2;
        }

        int commonDenominator = denominator1 * denominator2 * (difficulty >= 3 ? denominator3 : 1);
        int newNumerator = numerator1 * (commonDenominator / denominator1)
                + numerator2 * (commonDenominator / denominator2)
                + (difficulty >= 3 ? numerator3 * (commonDenominator / denominator3) : 0);

        int gcd = findGCD(newNumerator, commonDenominator);
        int simplifiedNumerator = newNumerator / gcd;
        int simplifiedDenominator = commonDenominator / gcd;


        String questionText = buildQuestionText(numerator1, denominator1, numerator2, denominator2, numerator3, denominator3, difficulty);
        String answer = simplifiedDenominator == 1 ? String.valueOf(simplifiedNumerator) : simplifiedNumerator + "/" + simplifiedDenominator;
        System.out.println(questionText);
        System.out.println(answer);
        return new QuestionEntity("מתמטיקה","שברים", "חיבור שברים", questionText, answer);
    }

    private String buildQuestionText(int numerator1, int denominator1, int numerator2, int denominator2, int numerator3, int denominator3, int difficulty) {
        if (difficulty >= 3) {
            return String.format("%d/%d + %d/%d + %d/%d"+" = ?", numerator1, denominator1, numerator2, denominator2, numerator3, denominator3);
        } else {
            return String.format("%d/%d + %d/%d"+" = ?", numerator1, denominator1, numerator2, denominator2);
        }
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 10;
            case 2 -> 12;
            case 3 -> 14;
            case 4 -> 16;
            case 5 -> 18;
            default -> 18;
        };
    }

    private int findGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
