package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DerivativeQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        String function = getFunction(difficulty);

        String questionText = "מהי הנגזרת של הפונקציה: " + function + "?";
        System.out.println(questionText);

        String answer = computeDerivative(function);
        System.out.println(answer);
        return new QuestionEntity("מתמטיקה", "ניגזרות", questionText, answer);
    }

    private String getFunction(int difficulty) {
        StringBuilder function = new StringBuilder();

        int coefficient, exponent, b, c, d, e;

        exponent = random.nextInt(3) + 1;

        switch (difficulty) {
            case 1:
                coefficient = random.nextInt(5) + 1;
                function.append(coefficient).append("x^").append(exponent);
                break;
            case 2:
                coefficient = random.nextInt(5) + 1;
                b = random.nextInt(5) + 1;
                exponent = random.nextInt(3) + 1;
                function.append(coefficient).append("x^").append(exponent).append(" + ").append(b);
                break;
            case 3:
                coefficient = random.nextInt(5) + 1;
                b = random.nextInt(5) + 1;
                c = random.nextInt(5) + 1;
                exponent = random.nextInt(3) + 1;
                function.append(coefficient).append("x^").append(exponent).append(" + ").append(b).append("x^2 + ").append(c);
                break;
            case 4:
                coefficient = random.nextInt(5) + 1;
                b = random.nextInt(5) + 1;
                c = random.nextInt(5) + 1;
                d = random.nextInt(5) + 1;
                exponent = random.nextInt(3) + 1;
                function.append(coefficient).append("x^").append(exponent).append(" - ").append(b).append("x^3 + ").append(c).append("x^2 - ").append(d);
                break;
            case 5:
                coefficient = random.nextInt(5) + 1;
                b = random.nextInt(5) + 1;
                c = random.nextInt(5) + 1;
                d = random.nextInt(5) + 1;
                e = random.nextInt(5) + 1;
                exponent = random.nextInt(3) + 1;
                function.append(coefficient).append("x^").append(exponent).append(" - ").append(b).append("x^4 + ").append(c).append("x^3 - ").append(d).append("x^2 + ").append(e).append("x");
                break;
            default:
                throw new IllegalArgumentException("רמת קושי לא תקינה");
        }

        return function.toString();
    }
    public  String computeDerivative(String expression) {
        expression = expression.replaceAll("\\s+", "");
        ArrayList<String> terms = new ArrayList<>();
        Pattern termPattern = Pattern.compile("([+-]?\\d*)x\\^?(\\d*)|([+-]?\\d+)");
        Matcher matcher = termPattern.matcher(expression);

        while (matcher.find()) {
            String coefficient = matcher.group(1);
            String power = matcher.group(2);
            String constant = matcher.group(3);
            if (coefficient != null && power != null) {
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
            } else if (coefficient != null) {
                int a = coefficient.isEmpty() || coefficient.equals("+") ? 1 :
                        coefficient.equals("-") ? -1 : Integer.parseInt(coefficient);
                terms.add(String.valueOf(a));
            } else if (constant != null) {
            }
        }
        String answer=String.join(" + ", terms).replaceAll("\\+ -", "- ");
        System.out.println(answer);
        return String.join(" + ", terms).replaceAll("\\+ -", "- ");
    }
}
