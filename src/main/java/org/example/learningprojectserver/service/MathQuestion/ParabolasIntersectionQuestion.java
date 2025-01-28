package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class ParabolasIntersectionQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        // יצירת קואורדינטות רנדומליות
        int a = random.nextInt(max * 2 + 1) - max;
        int b = random.nextInt(max * 2 + 1) - max;
        int c = random.nextInt(max * 2 + 1) - max;

        int d = random.nextInt(max * 2 + 1) - max;
        int e = random.nextInt(max * 2 + 1) - max;
        int f = random.nextInt(max * 2 + 1) - max;

        // בניית הטקסט של השאלה
        String questionText = String.format("חשבו את נקודות החיתוך בין הפונקציות: y = %sx² + %sx + %s ו- y = %sx² + %sx + %s",
                a, b, c, d, e, f);

        // חישוב נקודות החיתוך
        String answer = calculateIntersection(a, b, c, d, e, f);

        System.out.println(questionText);
        System.out.println(answer);

        return new QuestionEntity("מתמטיקה", "חיתוך פרבולות", questionText, answer);
    }

    private String calculateIntersection(int a, int b, int c, int d, int e, int f) {
        // יצירת משוואה ריבועית לחיתוך הפרבולות
        int A = a - d;
        int B = b - e;
        int C = c - f;

        // חישוב הדלתא (Δ)
        int delta = B * B - 4 * A * C;

        if (delta > 0) {
            // אם יש שני פתרונות (חיתוך בשתי נקודות)
            int x1 = (-B + (int) Math.sqrt(delta)) / (2 * A);
            int x2 = (-B - (int) Math.sqrt(delta)) / (2 * A);

            // חישוב ערכי y עבור כל x
            int y1 = a * x1 * x1 + b * x1 + c;
            int y2 = a * x2 * x2 + b * x2 + c;

            return String.format("ישנם שני חיתוכים בנקודות: (%d, %d) ו-(%d, %d)", formatNumberForDisplay(x1), formatNumberForDisplay(y1), formatNumberForDisplay(x2), formatNumberForDisplay(y2));
        } else if (delta == 0) {
            // אם יש פתרון אחד (חיתוך בנקודה אחת)
            int x = -B / (2 * A);
            int y = a * x * x + b * x + c;

            return String.format("יש חיתוך אחד בנקודה: (%d, %d)",formatNumberForDisplay(x) ,  formatNumberForDisplay(y));
        } else {
            // אם אין פתרונות (אין חיתוך)
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
            default -> throw new IllegalArgumentException("דרגת קושי לא תקינה");
        };
    }

    private String formatNumberForDisplay(int number) {
        // הוספת תו כיווניות LTR רק אם צריך (מונע השפעה על מספרים שלמים)
        return number >= 0 ? Integer.toString(number) : "\u200E" + number;
    }
}
