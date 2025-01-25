package org.example.learningprojectserver.service.MathQuestion;

public class MathQuestionFactory {

    public static MathQuestion getMathQuestion(String subTopic) {
        return switch (subTopic.toLowerCase()) {
            case "חיבור" -> new AdditionQuestion();
            case "חיסור" -> new SubtractionQuestion();
            case "כפל" -> new MultiplicationQuestion();
            case "חילוק" -> new DivisionQuestion();
            case "חזקות" -> new ExponentiationQuestion();
            case "שורש ריבועי" -> new SquareRootQuestion();
            case "מישוואה עם נעלם אחד" -> new EquationWithOneUnknownQuestion();
            case "מערכת משוואות עם שני נעלמים" -> new EquationWithTwoUnknownsQuestion();
            case "שאלה מילולית" -> new WordProblemQuestion();
            case "משוואה ריבועית" -> new QuadraticEquationQuestion();
            default -> throw new IllegalArgumentException("תת-נושא לא נתמך: " + subTopic);
        };
    }
}