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
            // case "אחוזים" -> new PercentageQuestion();
            case "חיבור שברים" -> new FractionAdditionQuestion();
            case "חיסור שברים" -> new FractionSubtractionQuestion();
            case "כפל שברים" -> new FractionMultiplicationQuestion();
            //case "חיבור וחיסור שברים" -> new FractionAdditionSubtractionQuestion();//TODO לסדר איך התרגיל מוצג הסדר לא נכון
            case "חישוב זמן" -> new TimeProblemQuestion();//TODO לסדר איך התרגיל מוצג
            //case "חישוב הנחות" -> new DiscountQuestion();//TODO החישובים לא נכונים בכלל
            case "סדרות דינמיות" -> new DynamicSeriesQuestion();
            case "ניגזרות" -> new DerivativeQuestion();//TODO לא עובד צריך לסדר
            //case "" -> new
            case "מישוואה עם נעלם אחד" -> new EquationWithOneUnknownQuestion();
            case "מערכת משוואות עם שני נעלמים" -> new EquationWithTwoUnknownsQuestion();
            case "שאלה מילולית" -> new WordProblemQuestion();
            case "משוואה ריבועית" -> new QuadraticEquationQuestion();
            default -> throw new IllegalArgumentException("תת-נושא לא נתמך: " + subTopic);
        };
    }
}