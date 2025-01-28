package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DerivativeQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        // בחר פונקציה אקראית לפי רמת הקושי
        String function = getFunction(difficulty);

        // בנה את השאלה
        String questionText = "מהי הנגזרת של הפונקציה: " + function + "?";
        System.out.println(questionText);

        // מחשבים את הנגזרת
//        String answer = calculateDerivative(function);
        String answer = computeDerivative(function);
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
    public  String computeDerivative(String expression) {
        // הסרת רווחים מיותרים
        expression = expression.replaceAll("\\s+", "");

        // רשימה לאחסון מונחים חדשים אחרי חישוב הנגזרת
        ArrayList<String> terms = new ArrayList<>();

        // תבנית לזיהוי מונחים: ax^n, ax, או מספר קבוע
        Pattern termPattern = Pattern.compile("([+-]?\\d*)x\\^?(\\d*)|([+-]?\\d+)");
        Matcher matcher = termPattern.matcher(expression);

        while (matcher.find()) {
            String coefficient = matcher.group(1); // המקדמים (a)
            String power = matcher.group(2);       // החזקות (n)
            String constant = matcher.group(3);    // מספרים קבועים

            if (coefficient != null && power != null) { // ax^n
                int a = coefficient.isEmpty() || coefficient.equals("+") ? 1 :
                        coefficient.equals("-") ? -1 : Integer.parseInt(coefficient);
                int n = power.isEmpty() ? 1 : Integer.parseInt(power);

                if (n == 1) {
                    terms.add(String.valueOf(a));
                } else {
                    int newCoefficient = a * n;
                    int newPower = n - 1;
                    terms.add(newCoefficient + "x" + (newPower == 1 ? "" : "^" + newPower));
                }
            } else if (coefficient != null) { // ax
                int a = coefficient.isEmpty() || coefficient.equals("+") ? 1 :
                        coefficient.equals("-") ? -1 : Integer.parseInt(coefficient);
                terms.add(String.valueOf(a));
            } else if (constant != null) { // מספר קבוע
                // נגזרת של מספר קבוע היא 0, ולכן מתעלמים ממנו
            }
        }
        String answer=String.join(" + ", terms).replaceAll("\\+ -", "- ");
        System.out.println(answer);
        return String.join(" + ", terms).replaceAll("\\+ -", "- ");
    }
}
