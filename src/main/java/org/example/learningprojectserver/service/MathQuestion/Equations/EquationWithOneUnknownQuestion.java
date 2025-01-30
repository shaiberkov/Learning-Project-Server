package org.example.learningprojectserver.service.MathQuestion.Equations;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator;

import java.util.Random;

public class EquationWithOneUnknownQuestion implements QuestionGenerator {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int max = getMaxNumber(difficulty);

        int leftOperand1 = random.nextInt(max) + 1;
        int rightOperand1 = random.nextInt(max) + 1;
        int leftOperand2 = random.nextInt(max) + 1;
        int result;

        String questionText;
        String answer;

        boolean isLeftOperandX = random.nextBoolean();
        boolean isRightOperandX = random.nextBoolean();
        boolean isMultiplication = random.nextBoolean();

        if (difficulty > 3) {
            boolean isAddition = random.nextBoolean();
            if (isAddition) {

                if (isLeftOperandX) {
                    result = rightOperand1 + leftOperand2 + leftOperand1;
                    questionText = String.format("x + %d + %d = %d", rightOperand1, leftOperand2, result);
                    answer = String.valueOf(leftOperand1);
                } else if (isRightOperandX) {
                    result = leftOperand1 + leftOperand2 + rightOperand1;
                    questionText = String.format("%d + %d + x = %d", leftOperand1, leftOperand2, result);
                    answer = String.valueOf(rightOperand1);
                } else if (isMultiplication) {
                    result = leftOperand1 * rightOperand1 + leftOperand2;
                    questionText = String.format("%dX + %d = %d", leftOperand1, leftOperand2, result);
                    answer = String.valueOf(rightOperand1);
                } else {
                    result = leftOperand1 + rightOperand1 + leftOperand2;
                    questionText = String.format("%d + %d + %d = x", leftOperand1, rightOperand1, leftOperand2);
                    answer = String.valueOf(result);
                }
            } else {

                if (isLeftOperandX) {
                    result = leftOperand1 - rightOperand1 - leftOperand2;
                    questionText = String.format("x - %d - %d = %d", rightOperand1, leftOperand2, result);
                    answer = String.valueOf(leftOperand1);
                } else if (isRightOperandX) {
                    result = leftOperand1 - leftOperand2 - rightOperand1;
                    questionText = String.format("%d - %d - x = %d", leftOperand1, leftOperand2, result);
                    answer = String.valueOf(rightOperand1);
                } else if (isMultiplication) {
                    result = leftOperand1 * rightOperand1 - leftOperand2;
                    questionText = String.format("%dX - %d = %d", leftOperand1, leftOperand2, result);
                    answer = String.valueOf(rightOperand1);
                } else {
                    result = leftOperand1 - rightOperand1 - leftOperand2;
                    questionText = String.format("%d - %d - %d = x", leftOperand1, rightOperand1, leftOperand2);
                    answer = String.valueOf(result);
                }
            }
        } else {

            boolean isAddition = random.nextBoolean();
            if (isAddition) {
                if (isLeftOperandX) {
                    result = rightOperand1 + leftOperand1;
                    questionText = String.format("x + %d = %d", rightOperand1, result);
                    answer = String.valueOf(leftOperand1);
                } else if (isRightOperandX) {
                    result = leftOperand1 + rightOperand1;
                    questionText = String.format("%d + x = %d", leftOperand1, result);
                    answer = String.valueOf(rightOperand1);
                } else if (isMultiplication) {
                    result = leftOperand1 * rightOperand1;
                    questionText = String.format("%dX = %d", leftOperand1, result);
                    answer = String.valueOf(rightOperand1);
                } else {
                    result = leftOperand1 + rightOperand1;
                    questionText = String.format("%d + %d = x", leftOperand1, rightOperand1);
                    answer = String.valueOf(result);
                }
            } else {
                if (isLeftOperandX) {
                    result = leftOperand1 - rightOperand1;
                    questionText = String.format("x - %d = %d", rightOperand1, result);
                    answer = String.valueOf(leftOperand1);
                } else if (isRightOperandX) {
                    result = leftOperand1 - rightOperand1;
                    questionText = String.format("%d - x = %d", leftOperand1, result);
                    answer = String.valueOf(rightOperand1);
                } else if (isMultiplication) {
                    result = leftOperand1 * rightOperand1;
                    questionText = String.format("%dX = %d", leftOperand1, result);
                    answer = String.valueOf(rightOperand1);
                } else {
                    result = leftOperand1 - rightOperand1;
                    questionText = String.format("%d - %d = x", leftOperand1, rightOperand1);
                    answer = String.valueOf(result);
                }
            }
        }

        return new QuestionEntity("מתמטיקה","משוואות", "מישוואה עם נעלם אחד", questionText, answer);
    }

    private int getMaxNumber(int difficulty) {
        return switch (difficulty) {
            case 1 -> 10;
            case 2 -> 20;
            case 3 -> 50;
            case 4 -> 100;
            case 5 -> 200;
            default -> 200;
        };
    }
}
