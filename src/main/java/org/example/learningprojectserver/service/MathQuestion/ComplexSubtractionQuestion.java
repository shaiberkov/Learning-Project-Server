package org.example.learningprojectserver.service.MathQuestion;


import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class ComplexSubtractionQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        int real1 = random.nextInt(max) + 1;
        int imaginary1 = random.nextInt(max) + 1;

        int real2 = random.nextInt(max) + 1;
        int imaginary2 = random.nextInt(max) + 1;

        int real3 = 0, imaginary3 = 0;
        if (difficulty > 2) {
            real3 = random.nextInt(max) + 1;
            imaginary3 = random.nextInt(max) + 1;
        }

        String questionText = buildQuestionText(real1, imaginary1, real2, imaginary2, real3, imaginary3);

        String answer = calculateComplexDifference(real1, imaginary1, real2, imaginary2, real3, imaginary3);
        System.out.println(questionText);
        System.out.println(answer);

        return new QuestionEntity("מתמטיקה", "חיסור מספרים מרוכבים", questionText, answer);
    }

    private String buildQuestionText(int real1, int imaginary1, int real2, int imaginary2, int real3, int imaginary3) {
        if (real3 == 0 && imaginary3 == 0) {
            return String.format("(%s + %si) - (%s + %si) = ?",
                    formatNumberForDisplay(real1), formatNumberForDisplay(imaginary1),
                    formatNumberForDisplay(real2), formatNumberForDisplay(imaginary2));
        } else {
            return String.format("(%s + %si) - (%s + %si) - (%s + %si) = ?",
                    formatNumberForDisplay(real1), formatNumberForDisplay(imaginary1),
                    formatNumberForDisplay(real2), formatNumberForDisplay(imaginary2),
                    formatNumberForDisplay(real3), formatNumberForDisplay(imaginary3));
        }
    }

    private String calculateComplexDifference(int real1, int imaginary1, int real2, int imaginary2, int real3, int imaginary3) {
        int realDifference = real1 - real2 - real3;
        int imaginaryDifference = imaginary1 - imaginary2 - imaginary3;
        if (imaginaryDifference == 0) {
            return formatNumberForDisplay(realDifference);
        }
        else if (imaginaryDifference < 0) {
            return String.format("%s %s %si", formatNumberForDisplay(realDifference), "-", formatNumberForDisplay(Math.abs(imaginaryDifference)));
        } else {
            return String.format("%s + %si", formatNumberForDisplay(realDifference), formatNumberForDisplay(imaginaryDifference));
        }
    }

    private String formatNumberForDisplay(int number) {
        return "\u200E" + number;
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 10;
            case 2 -> 50;
            case 3 -> 100;
            case 4 -> 500;
            case 5 -> 1000;
            default -> throw new IllegalArgumentException("דרגת קושי לא תקינה");
        };
    }
}
