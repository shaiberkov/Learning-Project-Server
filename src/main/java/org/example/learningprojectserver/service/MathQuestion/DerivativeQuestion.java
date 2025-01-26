package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class DerivativeQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        // בחר פונקציה אקראית לפי רמת הקושי
        String function = getFunction(difficulty);

        // בנה את השאלה
        String questionText = "מהי הנגזרת של הפונקציה: " + function + "?";

        // מחשבים את הנגזרת
        String answer = calculateDerivative(function);

        return new QuestionEntity("מתמטיקה", "ניגזרות", questionText, answer);
    }

    // בחר פונקציה אקראית לפי רמת הקושי
    private String getFunction(int difficulty) {
        StringBuilder function = new StringBuilder();

        // מקדם אקראי לכל חזקת x
        int coefficient, exponent, b, c, d, e;

        // נבחרת החזקה אקראית
        exponent = random.nextInt(3) + 1; // החזקה יכולה להיות בין 1 ל-3

        switch (difficulty) {
            case 1:
                coefficient = random.nextInt(5) + 1; // מקדם אקראי
                function.append(coefficient).append("x^").append(exponent);
                break;
            case 2:
                coefficient = random.nextInt(5) + 1;
                b = random.nextInt(5) + 1;
                exponent = random.nextInt(3) + 1; // חזקה אקראית
                function.append(coefficient).append("x^").append(exponent).append(" + ").append(b);
                break;
            case 3:
                coefficient = random.nextInt(5) + 1;
                b = random.nextInt(5) + 1;
                c = random.nextInt(5) + 1;
                exponent = random.nextInt(3) + 1; // חזקה אקראית
                function.append(coefficient).append("x^").append(exponent).append(" + ").append(b).append("x^2 + ").append(c);
                break;
            case 4:
                coefficient = random.nextInt(5) + 1;
                b = random.nextInt(5) + 1;
                c = random.nextInt(5) + 1;
                d = random.nextInt(5) + 1;
                exponent = random.nextInt(3) + 1; // חזקה אקראית
                function.append(coefficient).append("x^").append(exponent).append(" - ").append(b).append("x^3 + ").append(c).append("x^2 - ").append(d);
                break;
            case 5:
                coefficient = random.nextInt(5) + 1;
                b = random.nextInt(5) + 1;
                c = random.nextInt(5) + 1;
                d = random.nextInt(5) + 1;
                e = random.nextInt(5) + 1;
                exponent = random.nextInt(3) + 1; // חזקה אקראית
                function.append(coefficient).append("x^").append(exponent).append(" - ").append(b).append("x^4 + ").append(c).append("x^3 - ").append(d).append("x^2 + ").append(e).append("x");
                break;
            default:
                throw new IllegalArgumentException("רמת קושי לא תקינה");
        }

        return function.toString();
    }

    // מחשבים את הנגזרת של הפונקציה
    private String calculateDerivative(String function) {
        StringBuilder derivative = new StringBuilder();

        // פוקנציה גזירה של חזקה כללית
        if (function.contains("x^5")) {
            int coefficient = getCoefficient(function, "x^5");
            derivative.append(coefficient * 5).append("x^4");
        }
        if (function.contains("x^4") && !function.contains("x^5")) {
            int coefficient = getCoefficient(function, "x^4");
            derivative.append(coefficient * 4).append("x^3");
        }
        if (function.contains("x^3") && !function.contains("x^4")) {
            int coefficient = getCoefficient(function, "x^3");
            derivative.append(coefficient * 3).append("x^2");
        }
        if (function.contains("x^2") && !function.contains("x^3")) {
            int coefficient = getCoefficient(function, "x^2");
            derivative.append(coefficient * 2).append("x");
        }
        if (function.contains("x") && !function.contains("x^2")) {
            int coefficient = getCoefficient(function, "x");
            derivative.append(coefficient);
        }
        if (derivative.length() == 0) {
            throw new IllegalArgumentException("פונקציה לא נתמכת");
        }

        return derivative.toString();
    }

    // מקבל את הקבוע מתוך הפונקציה, לדוגמה עבור x^2 או x^3
    private int getCoefficient(String function, String term) {
        String[] parts = function.split(term);
        String coefficientStr = parts[0].trim();

        // אם הקבוע הוא חיובי או שלילי או לא מוגדר, יש להחזיר ערך ברירת מחדל
        if (coefficientStr.equals("") || coefficientStr.equals("+")) {
            return 1;
        } else if (coefficientStr.equals("-")) {
            return -1;
        } else {
            return Integer.parseInt(coefficientStr);
        }
    }
}
