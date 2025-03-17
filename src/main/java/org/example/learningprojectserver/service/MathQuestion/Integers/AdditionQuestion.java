package org.example.learningprojectserver.service.MathQuestion.Integers;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;

import java.util.Random;

public class AdditionQuestion implements QuestionGenerator {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        int numCount = (difficulty >= 3 && difficulty <= 5) ? 3 : 2;

        int[] numbers = new int[numCount];
        for (int i = 0; i < numCount; i++) {
            numbers[i] = random.nextInt(max) + 1;
        }

        String questionText = buildQuestionText(numbers);
        String answer = String.valueOf(calculateSum(numbers));

        return new QuestionEntity("מתמטיקה","מספרים שלמים", "חיבור מספרים שלמים", questionText, answer);
    }


    private String buildQuestionText(int[] numbers) {
        StringBuilder questionBuilder = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            questionBuilder.append(numbers[i]);
            if (i < numbers.length - 1) {
                questionBuilder.append("+");
            }
        }
        questionBuilder.append("=");
        questionBuilder.append("?");
        return questionBuilder.toString();
    }

    private int calculateSum(int[] numbers) {
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return sum;
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
}
