//package org.example.learningprojectserver.service.MathQuestion;
//
//import org.example.learningprojectserver.entities.QuestionEntity;
//
//import java.util.Random;
//
//public class MatrixAdditionQuestion implements MathQuestion {
//
//    private final Random random = new Random();
//
//    @Override
//    public QuestionEntity generateQuestion(int difficulty) {
//        int size = getMatrixSize(difficulty);
//
//        int[][] matrix1 = generateRandomMatrix(size);
//        int[][] matrix2 = generateRandomMatrix(size);
//
//        String questionText = buildQuestionText(matrix1, matrix2);
//        String answer = calculateMatrixSum(matrix1, matrix2);
//
//        System.out.println(questionText);
//        System.out.println(answer);
//
//        return new QuestionEntity("מתמטיקה", "חיבור מטריצות", questionText, answer);
//    }
//
//    private int[][] generateRandomMatrix(int size) {
//        int[][] matrix = new int[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                matrix[i][j] = random.nextInt(10) + 1; // Random number between 1 and 10
//            }
//        }
//        return matrix;
//    }
//
//    private String buildQuestionText(int[][] matrix1, int[][] matrix2) {
//        StringBuilder question = new StringBuilder("חבר את המטריצות:\n");
//        question.append("מטריצה 1:\n");
//        appendMatrix(question, matrix1);
//        question.append("מטריצה 2:\n");
//        appendMatrix(question, matrix2);
//        question.append("תוצאה:\n");
//        return question.toString();
//    }
//
//    private void appendMatrix(StringBuilder question, int[][] matrix) {
//        for (int[] row : matrix) {
//            for (int value : row) {
//                question.append(formatNumberForDisplay(value)).append(" ");
//            }
//            question.append("\n");
//        }
//    }
//
//    private String calculateMatrixSum(int[][] matrix1, int[][] matrix2) {
//        int size = matrix1.length;
//        int[][] sumMatrix = new int[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                sumMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
//            }
//        }
//
//        StringBuilder answer = new StringBuilder();
//        appendMatrix(answer, sumMatrix);
//        return answer.toString();
//    }
//
//    private String formatNumberForDisplay(int number) {
//        return "\u200E" + number;
//    }
//
//    private int getMatrixSize(int difficulty) {
//        return switch (difficulty) {
//            case 1 -> 2;
//            case 2 -> 3;
//            case 3 -> 4;
//            case 4 -> 5;
//            case 5 -> 6;
//            default -> throw new IllegalArgumentException("דרגת קושי לא תקינה");
//        };
//    }
//}

package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class MatrixAdditionQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int size = getMatrixSize(difficulty);

        int[][] matrix1 = generateRandomMatrix(size);
        int[][] matrix2 = generateRandomMatrix(size);

        String questionText = buildQuestionText(matrix1, matrix2);
        String answer = calculateMatrixSum(matrix1, matrix2);

        System.out.println(questionText);
        System.out.println(answer);

        return new QuestionEntity("מתמטיקה", "חיבור מטריצות", questionText, answer);
    }

    private int[][] generateRandomMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextInt(10) + 1; // Random number between 1 and 10
            }
        }
        return matrix;
    }

    private String buildQuestionText(int[][] matrix1, int[][] matrix2) {
        StringBuilder question = new StringBuilder();

        // Add first matrix in the format with each row on a new line
        appendMatrix(question, matrix1);

        question.append(" + "); // Plus sign in the middle

        // Add second matrix in the format with each row on a new line
        appendMatrix(question, matrix2);

        question.append(" = ?");

        return question.toString();
    }

    private void appendMatrix(StringBuilder question, int[][] matrix) {
        // Append each row of the matrix, one by one
        for (int i = 0; i < matrix.length; i++) {
            question.append("\n");
            for (int j = 0; j < matrix[i].length; j++) {
                question.append(formatNumberForDisplay(matrix[i][j]));
                if (j < matrix[i].length - 1) {
                    question.append(" ");  // Space between elements in the row
                }
            }
        }
    }

    private String calculateMatrixSum(int[][] matrix1, int[][] matrix2) {
        int size = matrix1.length;
        int[][] sumMatrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sumMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        StringBuilder answer = new StringBuilder();
        appendMatrix(answer, sumMatrix);
        return answer.toString();
    }

    private String formatNumberForDisplay(int number) {
        return "\u200E" + number;
    }

    private int getMatrixSize(int difficulty) {
        return switch (difficulty) {
            case 1 -> 2;
            case 2 -> 3;
            case 3 -> 4;
            case 4 -> 5;
            case 5 -> 6;
            default -> throw new IllegalArgumentException("דרגת קושי לא תקינה");
        };
    }
}
