package org.example.learningprojectserver.service.MathQuestion.Vectors;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator.QuestionGenerator;

import java.util.Random;

public class VectorDotProductQuestion implements QuestionGenerator {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int dimension = getVectorDimension(difficulty);

        int[] vector1 = generateRandomVector(dimension);
        int[] vector2 = generateRandomVector(dimension);

        String questionText = buildQuestionText(vector1, vector2);
        String answer = calculateDotProduct(vector1, vector2);

        System.out.println(questionText);
        System.out.println(answer);

        return new QuestionEntity("מתמטיקה","וקטורים", "מכפלה סקלרית של וקטורים", questionText, answer);
    }

    private int[] generateRandomVector(int dimension) {
        int[] vector = new int[dimension];
        for (int i = 0; i < dimension; i++) {
            vector[i] = random.nextInt(21) - 10;
        }
        return vector;
    }

    private String buildQuestionText(int[] vector1, int[] vector2) {
        StringBuilder question = new StringBuilder();
        question.append("(");
        for (int i = 0; i < vector1.length; i++) {
            question.append(formatNumberForDisplay(vector1[i]));
            if (i < vector1.length - 1) {
                question.append(", ");
            }
        }
        question.append(")");
        question.append(" ⋅ ");
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

    private String calculateDotProduct(int[] vector1, int[] vector2) {
        int dotProduct = 0;
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }

        return formatNumberForDisplay(dotProduct);
    }

    private String formatNumberForDisplay(int number) {
        return "\u200E" + number;
    }

    private int getVectorDimension(int difficulty) {
        return switch (difficulty) {
            case 1 -> 2;
            case 2 -> 3;
            case 3 -> 4;
            case 4 -> 5;
            case 5 -> 6;
            default -> 6;
        };
    }
}
