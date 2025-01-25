package org.example.learningprojectserver.service.MathQuestion;


import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class QuadraticEquationQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        // בחר את מקדמי המשוואה כך שדלתא תהיה ריבוע מושלם
        int a = random.nextInt(max) + 1;
        int b = random.nextInt(max) + 1;
        int c = random.nextInt(max) + 1;

        // נוודא שדלתא היא ריבוע מושלם על ידי התאמת ערך b ו-c
        int discriminant = b * b - 4 * a * c;
        while (!isPerfectSquare(discriminant)) {
            // אם דלתא לא ריבוע מושלם, בחר מחדש את c כך שדלתא תהיה ריבוע מושלם
            c = random.nextInt(max) + 1;
            discriminant = b * b - 4 * a * c;
        }

        // השאלה היא משוואה ריבועית בצורה ax² + bx + c = 0
        String questionText = String.format("%dx² + %dx + %d = 0", a, b, c);

        // חישוב תשובה על פי נוסחת השורשים למשוואה ריבועית: (-b ± √(b² - 4ac)) / 2a
        double sqrtDiscriminant = Math.sqrt(discriminant);
        int root1 = (-b + (int) sqrtDiscriminant) / (2 * a);
        int root2 = (-b - (int) sqrtDiscriminant) / (2 * a);

        String answer = String.format("x1 = %d, x2 = %d", root1, root2);

        return new QuestionEntity("מתמטיקה", "משוואה ריבועית", questionText, answer);
    }

    // פונקציה לבדיקת האם המספר הוא ריבוע מושלם
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