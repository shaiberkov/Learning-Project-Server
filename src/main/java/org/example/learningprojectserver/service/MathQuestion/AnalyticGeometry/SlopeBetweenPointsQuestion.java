package org.example.learningprojectserver.service.MathQuestion.AnalyticGeometry;


import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator;

import java.util.Random;

public class SlopeBetweenPointsQuestion implements QuestionGenerator {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        int x1 = random.nextInt(max * 2 + 1) - max;
        int y1 = random.nextInt(max * 2 + 1) - max;
        int x2;
        int y2;

        do {
            x2 = random.nextInt(max * 2 + 1) - max;
            y2 = random.nextInt(max * 2 + 1) - max;
        } while (x1 == x2 || !isIntegerSlope(x1, y1, x2, y2));

        String questionText = String.format("חשבו את השיפוע בין הנקודות (%s, %s) ו-(%s, %s)",
                formatNumberForDisplay(x1), formatNumberForDisplay(y1),
                formatNumberForDisplay(x2), formatNumberForDisplay(y2));

        int slope = calculateSlope(x1, y1, x2, y2);

        String answer = String.valueOf(slope);

        System.out.println(questionText);
        System.out.println(answer);
        return new QuestionEntity("מתמטיקה","הנדסה אנליטית", "חישוב שיפוע", questionText, answer);
    }

    private int calculateSlope(int x1, int y1, int x2, int y2) {
        return (y2 - y1) / (x2 - x1);
    }

    private boolean isIntegerSlope(int x1, int y1, int x2, int y2) {
        int deltaY = y2 - y1;
        int deltaX = x2 - x1;

        return deltaX != 0 && deltaY % deltaX == 0;
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 10;
            case 2 -> 50;
            case 3 -> 100;
            case 4 -> 500;
            case 5 -> 1000;
            default -> 1000;
        };
    }

    private String formatNumberForDisplay(int number) {
        return "\u200E" + number;
    }
}

