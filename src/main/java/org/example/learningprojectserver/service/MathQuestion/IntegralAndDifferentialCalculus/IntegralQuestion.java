package org.example.learningprojectserver.service.MathQuestion.IntegralAndDifferentialCalculus;


import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegralQuestion implements QuestionGenerator {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        // בחר פונקציה אקראית לפי רמת הקושי
        String function = getFunction(difficulty);

        // בנה את השאלה
        String questionText = "מהו האינטגרל של הפונקציה: " + function + " ?";
        System.out.println(questionText);

        // מחשבים את האינטגרל
        String answer = computeIntegral(function);
        System.out.println(answer);
        return new QuestionEntity("מתמטיקה","חשבון אינטגרלי ודפרנציאלי", "אינטגרלים", questionText, answer);
    }

    // בחר פונקציה אקראית לפי רמת הקושי
    private String getFunction(int difficulty) {
        StringBuilder function = new StringBuilder();

        int coefficient, exponent, b, c, d, e;

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
    public String computeIntegral(String expression) {
        // הסרת רווחים מיותרים
        expression = expression.replaceAll("\\s+", "");

        // רשימה לאחסון מונחים חדשים אחרי חישוב האינטגרל
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

                int newPower = n + 1;
                int newCoefficient = a / newPower;

                // הוספת המונח החדש
                terms.add(newCoefficient + "x" + (newPower == 1 ? "" : "^" + newPower));
            } else if (coefficient != null) { // ax
                int a = coefficient.isEmpty() || coefficient.equals("+") ? 1 :
                        coefficient.equals("-") ? -1 : Integer.parseInt(coefficient);
                terms.add(a + "x");
            } else if (constant != null) { // מספר קבוע
                int c = Integer.parseInt(constant);
                terms.add(c + "x");
            }
        }

        // איחוד כל האינטגרלים והחזרתן
        return String.join(" + ", terms).replaceAll("\\+ -", "- ");
    }
//    public static String computeIntegral(String expression) {
//        // הסרת רווחים מיותרים
//        expression = expression.replaceAll("\\s+", "");
//
//        // רשימה לאחסון מונחים חדשים אחרי חישוב האינטגרל
//        ArrayList<String> terms = new ArrayList<>();
//
//        // תבנית לזיהוי מונחים: ax^n, ax, או מספר קבוע
//        Pattern termPattern = Pattern.compile("([+-]?\\d*)x\\^?(\\d*)|([+-]?\\d+)");
//        Matcher matcher = termPattern.matcher(expression);
//
//        while (matcher.find()) {
//            String coefficient = matcher.group(1); // המקדמים (a)
//            String power = matcher.group(2);       // החזקות (n)
//            String constant = matcher.group(3);    // מספרים קבועים
//
//            if (coefficient != null && power != null) { // ax^n
//                int a = coefficient.isEmpty() || coefficient.equals("+") ? 1 :
//                        coefficient.equals("-") ? -1 : Integer.parseInt(coefficient);
//                int n = power.isEmpty() ? 1 : Integer.parseInt(power);
//
//                int newPower = n + 1;
//                int newCoefficient = a / newPower;
//
//                // הוספת המונח החדש
//                terms.add(newCoefficient + "x" + (newPower == 1 ? "" : "^" + newPower));
//            } else if (coefficient != null) { // ax
//                int a = coefficient.isEmpty() || coefficient.equals("+") ? 1 :
//                        coefficient.equals("-") ? -1 : Integer.parseInt(coefficient);
//                terms.add(a + "x");
//            } else if (constant != null) { // מספר קבוע
//                int c = Integer.parseInt(constant);
//                terms.add(c + "x");
//            }
//        }
//
//        // איחוד כל האינטגרלים והחזרתן
//        return String.join(" + ", terms).replaceAll("\\+ -", "- ");
//    }
    // פונקציה לחישוב האינטגרל של פונקציה שניתנה כ-string
//    public String computeIntegral(String expression) {
//        // הסרת רווחים מיותרים
//        expression = expression.replaceAll("\\s+", "");
//
//        // רשימה לאחסון מונחים חדשים אחרי חישוב האינטגרל
//        ArrayList<String> terms = new ArrayList<>();
//
//        // תבנית לזיהוי מונחים: ax^n, ax, או מספר קבוע
//        Pattern termPattern = Pattern.compile("([+-]?\\d*)x\\^?(\\d*)|([+-]?\\d+)");
//        Matcher matcher = termPattern.matcher(expression);
//
//        while (matcher.find()) {
//            String coefficient = matcher.group(1); // המקדמים (a)
//            String power = matcher.group(2);       // החזקות (n)
//            String constant = matcher.group(3);    // מספרים קבועים
//
//            if (coefficient != null && power != null) { // ax^n
//                int a = coefficient.isEmpty() || coefficient.equals("+") ? 1 :
//                        coefficient.equals("-") ? -1 : Integer.parseInt(coefficient);
//                int n = power.isEmpty() ? 1 : Integer.parseInt(power);
//
//                // חישוב האינטגרל
//                int newCoefficient = a / (n + 1);
//                int newPower = n + 1;
//                terms.add(newCoefficient + "x" + (newPower == 1 ? "" : "^" + newPower));
//            } else if (coefficient != null) { // ax
//                int a = coefficient.isEmpty() || coefficient.equals("+") ? 1 :
//                        coefficient.equals("-") ? -1 : Integer.parseInt(coefficient);
//                terms.add(a + "x");
//            } else if (constant != null) { // מספר קבוע
//                // אינטגרל של מספר קבוע הוא מספר קבוע כפול x
//                terms.add(constant + "x");
//            }
//        }
//
//        // הצגת התשובה עם סימני "+" בצורה נכונה
//        String answer = String.join(" + ", terms).replaceAll("\\+ -", "- ");
//        System.out.println(answer);
//        return answer;
//    }
}
