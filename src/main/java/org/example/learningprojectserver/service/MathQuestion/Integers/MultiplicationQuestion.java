package org.example.learningprojectserver.service.MathQuestion.Integers;


import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;

import java.util.Random;

public class MultiplicationQuestion implements QuestionGenerator {

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
        String answer = String.valueOf(calculateProduct(numbers));

        return new QuestionEntity("מתמטיקה", "מספרים שלמים","כפל מספרים שלמים", questionText, answer);
    }

    private String buildQuestionText(int[] numbers) {
        StringBuilder questionBuilder = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            questionBuilder.append(numbers[i]);
            if (i < numbers.length - 1) {
                questionBuilder.append("×");
            }
        }
        questionBuilder.append("=");
        questionBuilder.append("?");
        return questionBuilder.toString();
    }

    private int calculateProduct(int[] numbers) {
        int product = 1;
        for (int num : numbers) {
            product *= num;
        }
        return product;
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 5;
            case 2 -> 7;
            case 3 -> 7;
            case 4 -> 8;
            case 5 -> 10;
            default -> 10;
        };
    }
}

