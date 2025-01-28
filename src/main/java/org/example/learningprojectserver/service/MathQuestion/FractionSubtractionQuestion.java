package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class FractionSubtractionQuestion implements MathQuestion {

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
        int scaledNumerator1 = numerator1 * (commonDenominator / denominator1);
        int scaledNumerator2 = numerator2 * (commonDenominator / denominator2);
        int scaledNumerator3 = (difficulty >= 3 ? numerator3 * (commonDenominator / denominator3) : 0);


        int totalNumerator = scaledNumerator1 - scaledNumerator2 - (difficulty >= 3 ? scaledNumerator3 : 0);
        while (totalNumerator < 0) {
            numerator1 = random.nextInt(max) + 1;
            denominator1 = random.nextInt(max - 1) + 2;
            numerator2 = random.nextInt(max) + 1;
            denominator2 = random.nextInt(max - 1) + 2;

            if (difficulty >= 3) {
                numerator3 = random.nextInt(max) + 1;
                denominator3 = random.nextInt(max - 1) + 2;
            }

            commonDenominator = denominator1 * denominator2 * (difficulty >= 3 ? denominator3 : 1);
            scaledNumerator1 = numerator1 * (commonDenominator / denominator1);
            scaledNumerator2 = numerator2 * (commonDenominator / denominator2);
            scaledNumerator3 = (difficulty >= 3 ? numerator3 * (commonDenominator / denominator3) : 0);

            totalNumerator = scaledNumerator1 - scaledNumerator2 - (difficulty >= 3 ? scaledNumerator3 : 0);
        }

        int gcd = findGCD(totalNumerator, commonDenominator);
        int simplifiedNumerator = totalNumerator / gcd;
        int simplifiedDenominator = commonDenominator / gcd;

        String questionText = buildQuestionText(numerator1, denominator1, numerator2, denominator2, numerator3, denominator3, difficulty);
        String answer = simplifiedDenominator == 1 ? String.valueOf(simplifiedNumerator) : simplifiedNumerator + "/" + simplifiedDenominator;
        System.out.println(questionText);
        System.out.println(answer);
        return new QuestionEntity("מתמטיקה", "חיסור שברים", questionText, answer);
    }

    private String buildQuestionText(int numerator1, int denominator1, int numerator2, int denominator2, int numerator3, int denominator3, int difficulty) {
        if (difficulty >= 3) {
            return String.format("%d/%d - %d/%d - %d/%d"+" = ?", numerator1, denominator1, numerator2, denominator2, numerator3, denominator3);
        } else {
            return String.format("%d/%d - %d/%d"+" = ?", numerator1, denominator1, numerator2, denominator2);
        }
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 10;
            case 2 -> 12;
            case 3 -> 14;
            case 4 -> 16;
            case 5 -> 18;
            default -> throw new IllegalArgumentException("דרגת קושי לא תקינה");
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
