package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class FractionAdditionSubtractionQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        // יצירת שני שברים אקראיים
        int numerator1 = random.nextInt(max) + 1;
        int denominator1 = random.nextInt(max - 1) + 2;
        int numerator2 = random.nextInt(max) + 1;
        int denominator2 = random.nextInt(max - 1) + 2;

        // שבר שלישי עבור דרגת קושי 3 ומעלה
        int numerator3 = 0;
        int denominator3 = 0;
        String operator2 = "";
        if (difficulty >= 3) {
            numerator3 = random.nextInt(max) + 1;
            denominator3 = random.nextInt(max - 1) + 2;

            // אקראי אם השאלה תכלול חיבור או חיסור של השבר השלישי
            operator2 = random.nextBoolean() ? "+" : "-";
        }

        // אקראי אם השאלה תתחיל בחיבור או חיסור
        String operator1 = random.nextBoolean() ? "+" : "-";

        // חישוב השאלה
        int commonDenominator = denominator1 * denominator2 * (difficulty >= 3 ? denominator3 : 1);
        int newNumerator = numerator1 * (commonDenominator / denominator1)
                + (operator1.equals("+") ? numerator2 * (commonDenominator / denominator2) : -numerator2 * (commonDenominator / denominator2))
                + (difficulty >= 3 ? (operator2.equals("+") ? numerator3 * (commonDenominator / denominator3) : -numerator3 * (commonDenominator / denominator3)) : 0);

        // פישוט התוצאה
        int gcd = findGCD(newNumerator, commonDenominator);
        int simplifiedNumerator = newNumerator / gcd;
        int simplifiedDenominator = commonDenominator / gcd;

        // בניית השאלה
        String questionText = buildQuestionText(numerator1, denominator1, numerator2, denominator2, numerator3, denominator3, operator1, operator2, difficulty);
        String answer = simplifiedDenominator == 1 ? String.valueOf(simplifiedNumerator) : simplifiedNumerator + "/" + simplifiedDenominator;

        return new QuestionEntity("מתמטיקה", "חיבור וחיסור שברים", questionText, answer);
    }

    private String buildQuestionText(int numerator1, int denominator1, int numerator2, int denominator2, int numerator3, int denominator3, String operator1, String operator2, int difficulty) {
        // שומרים על הסדר הזה: השבר הראשון תמיד יהיה בצד שמאל
        if (difficulty >= 3) {
            return String.format("חשב את התוצאה: %d/%d %s %d/%d %s %d/%d", numerator1, denominator1, operator1, numerator2, denominator2, operator2, numerator3, denominator3);
        } else {
            return String.format("חשב את התוצאה: %d/%d %s %d/%d", numerator1, denominator1, operator1, numerator2, denominator2);
        }
    }

    // קביעת טווח המספרים לפי דרגת הקושי
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

    // מציאת מחלק משותף מקסימלי (GCD)
    private int findGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
