package org.example.learningprojectserver.service.MathQuestion.Integers;

import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator;

import java.util.Random;

public class DynamicSeriesQuestion implements QuestionGenerator {

    private final Random random = new Random();
    private String operation;
    private int number;

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int startingValue = getStartingValue(difficulty);
        int numTerms = 3 + difficulty;
        this.operation = getRandomOperation();

        String series = generateSeries(startingValue, numTerms);
        String questionText = String.format("השלם את הסדרה: ___%s ", series);
        String answer = String.format("%d", calculateNextTerm(startingValue, numTerms));
        System.out.println(questionText);
        System.out.println(answer);
        return new QuestionEntity("מתמטיקה","מספרים שלמים", "סדרות דינמיות", questionText, answer);
    }

    private String generateSeries(int startingValue, int numTerms) {
        this.number =random.nextInt(4)+2;
        StringBuilder series = new StringBuilder();
        for (int i = 0; i < numTerms - 1; i++) {
            series.append(reverseNumber(startingValue));
            series.append(", ");
            startingValue = applyOperation(startingValue);
        }

        return series.reverse().toString();
    }

    private int calculateNextTerm(int startingValue, int numTerms) {
        for (int i = 0; i < numTerms - 1; i++) {
            startingValue = applyOperation(startingValue);
        }
        return startingValue;
    }
    private int applyOperation(int currentValue) {
        int result;
        switch (operation) {
            case "multiply":
                result = currentValue * number;
                break;
            case "add":
                result = currentValue + number;
                break;
            case "subtract":
                result = currentValue - number;
                break;
            default:
                result = currentValue;
        }
        return Math.abs(result);
    }


    private String getRandomOperation() {
        int randomOp = random.nextInt(4);
        switch (randomOp) {
            case 0: return "multiply";
            case 1: return "add";
            case 2: return "subtract";
            default: return "multiply";
        }
    }

    private int getStartingValue(int difficulty) {
        switch (difficulty) {
            case 1: return 15;
            case 2: return 15;
            case 3: return 15;
            case 4: return 20;
            case 5: return 30;
            default: return 1;
        }
    }

    private String reverseNumber(int number) {
        return new StringBuilder(String.valueOf(number)).reverse().toString();
    }
}
