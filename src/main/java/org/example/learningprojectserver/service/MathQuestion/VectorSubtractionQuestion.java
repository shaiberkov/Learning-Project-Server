package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class VectorSubtractionQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int dimension = getVectorDimension(difficulty);

        int[] vector1 = generateRandomVector(dimension);
        int[] vector2 = generateRandomVector(dimension);

        String questionText = buildQuestionText(vector1, vector2);
        String answer = calculateVectorDifference(vector1, vector2);

        System.out.println(questionText);
        System.out.println(answer);

        return new QuestionEntity("מתמטיקה", "חיסור וקטורים", questionText, answer);
    }

    private int[] generateRandomVector(int dimension) {
        int[] vector = new int[dimension];
        for (int i = 0; i < dimension; i++) {
            // Generate random number between -10 and 10
            vector[i] = random.nextInt(21) - 10; // Random number between -10 and 10
        }
        return vector;
    }

    private String buildQuestionText(int[] vector1, int[] vector2) {
        StringBuilder question = new StringBuilder();

        // Add first vector in the format with each element inside the vector
        question.append("(");
        for (int i = 0; i < vector1.length; i++) {
            question.append(formatNumberForDisplay(vector1[i]));
            if (i < vector1.length - 1) {
                question.append(", ");
            }
        }
        question.append(")");

        question.append(" - "); // Minus sign in the middle

        // Add second vector in the format with each element inside the vector
        question.append("(");
        for (int i = 0; i < vector2.length; i++) {
            question.append(formatNumberForDisplay(vector2[i]));
            if (i < vector2.length - 1) {
                question.append(", ");
            }
        }
        question.append(")");

        question.append(" = ?");

        return question.toString();
    }

    private String calculateVectorDifference(int[] vector1, int[] vector2) {
        int[] diffVector = new int[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            diffVector[i] = vector1[i] - vector2[i];
        }

        StringBuilder answer = new StringBuilder();
        answer.append("(");
        for (int i = 0; i < diffVector.length; i++) {
            answer.append(formatNumberForDisplay(diffVector[i]));
            if (i < diffVector.length - 1) {
                answer.append(", ");
            }
        }
        answer.append(")");

        return answer.toString();
    }

    private String formatNumberForDisplay(int number) {
        return "\u200E" + number;
    }

    private int getVectorDimension(int difficulty) {
        return switch (difficulty) {
            case 1 -> 2;  // R² (2-dimensional vectors)
            case 2 -> 3;  // R³ (3-dimensional vectors)
            case 3 -> 4;
            case 4 -> 5;
            case 5 -> 6;
            default -> throw new IllegalArgumentException("דרגת קושי לא תקינה");
        };
    }
}
