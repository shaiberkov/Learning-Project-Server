package org.example.learningprojectserver.service.MathQuestion;


import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class IntersectionPointQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        int m1, m2, b1, b2;

        do {
            m1 = getRandomNonZeroCoefficient(max);
            m2 = getRandomNonZeroCoefficient(max);
            b1 = random.nextInt(max * 2 + 1) - max;
            b2 = random.nextInt(max * 2 + 1) - max;
        } while (m1 == m2 || !isIntegerIntersection(m1, b1, m2, b2)); // לוודא שיפוע שונה ונקודת חיתוך שלמה

        // בניית הטקסט של השאלה
        String questionText = String.format("מצאו את נקודת החיתוך בין הישרים: y = %sx + %s ו-y = %sx + %s",
                formatNumberForDisplay(m1), formatNumberForDisplay(b1),
                formatNumberForDisplay(m2), formatNumberForDisplay(b2));

        // חישוב נקודת החיתוך
        int[] intersection = calculateIntersection(m1, b1, m2, b2);
        String answer = String.format("(%d, %d)", intersection[0], intersection[1]);

        System.out.println(questionText);
        System.out.println(answer);

        return new QuestionEntity("מתמטיקה", "נקודת חיתוך", questionText, answer);
    }

    private int[] calculateIntersection(int m1, int b1, int m2, int b2) {
        int x = (b2 - b1) / (m1 - m2);
        int y = m1 * x + b1;
        return new int[]{x, y};
    }

    private boolean isIntegerIntersection(int m1, int b1, int m2, int b2) {
        int deltaM = m1 - m2;
        int deltaB = b2 - b1;

        // בדיקה אם נקודת החיתוך שלמה
        return deltaM != 0 && deltaB % deltaM == 0;
    }

    private int getRandomNonZeroCoefficient(int max) {
        int coefficient;
        do {
            coefficient = random.nextInt(max * 2 + 1) - max;
        } while (coefficient == 0);
        return coefficient;
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

    private String formatNumberForDisplay(int number) {
        // הוספת תו כיווניות LTR
        return "\u200E" + number;
    }
}

