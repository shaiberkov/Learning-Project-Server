package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class PercentageQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        // בחירת אחוז אקראי בין 1 ל-100
        int percentage = random.nextInt(100) + 1;

        // יצירת תוצאה שלמה אקראית
        int result = random.nextInt(max) + 1;

        // חישוב מספר בסיסי כך שהתוצאה תהיה שלמה
        int baseNumber = (result * 100) / percentage;

        // בניית השאלה
        String questionText = buildQuestionText(baseNumber, percentage);
        String answer = String.valueOf(result);

        return new QuestionEntity("מתמטיקה", "אחוזים", questionText, answer);
    }

    private String buildQuestionText(int baseNumber, int percentage) {
        return String.format("כמה זה %d%% מתוך %d?", percentage, baseNumber);
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 50;  // תוצאה מקסימלית קטנה
            case 2 -> 100;
            case 3 -> 200;
            case 4 -> 500;
            case 5 -> 1000; // תוצאה מקסימלית גדולה
            default -> throw new IllegalArgumentException("דרגת קושי לא תקינה");
        };
    }
}
