package org.example.learningprojectserver.service.MathQuestion.AnalyticGeometry;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator;

import java.util.Random;

public class ParabolasIntersectionQuestion implements QuestionGenerator {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        int a = random.nextInt(max * 2 + 1) - max;
        int b = random.nextInt(max * 2 + 1) - max;
        int c = random.nextInt(max * 2 + 1) - max;

        int d = random.nextInt(max * 2 + 1) - max;
        int e = random.nextInt(max * 2 + 1) - max;
        int f = random.nextInt(max * 2 + 1) - max;

        String questionText = String.format("חשבו את נקודות החיתוך בין הפונקציות: y = %sx² + %sx + %s ו- y = %sx² + %sx + %s",
                a, b, c, d, e, f);

        String answer = calculateIntersection(a, b, c, d, e, f);

        System.out.println(questionText);
        System.out.println(answer);

        return new QuestionEntity("מתמטיקה","הנדסה אנליטית", "חיתוך פרבולות", questionText, answer);
    }

    private String calculateIntersection(int a, int b, int c, int d, int e, int f) {
        int A = a - d;
        int B = b - e;
        int C = c - f;
        int delta = B * B - 4 * A * C;

        if (delta > 0) {
            int x1 = (-B + (int) Math.sqrt(delta)) / (2 * A);
            int x2 = (-B - (int) Math.sqrt(delta)) / (2 * A);

            int y1 = a * x1 * x1 + b * x1 + c;
            int y2 = a * x2 * x2 + b * x2 + c;

            return String.format("ישנם שני חיתוכים בנקודות: (%d, %d) ו-(%d, %d)", formatNumberForDisplay(x1), formatNumberForDisplay(y1), formatNumberForDisplay(x2), formatNumberForDisplay(y2));
        } else if (delta == 0) {
            int x = -B / (2 * A);
            int y = a * x * x + b * x + c;

            return String.format("יש חיתוך אחד בנקודה: (%d, %d)",formatNumberForDisplay(x) ,  formatNumberForDisplay(y));
        } else {
            return "אין חיתוך בין הפונקציות";
        }
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
        return number >= 0 ? Integer.toString(number) : "\u200E" + number;
    }
}
