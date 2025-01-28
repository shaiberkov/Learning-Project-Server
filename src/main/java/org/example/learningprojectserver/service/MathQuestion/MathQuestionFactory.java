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
            case "חיבור וחיסור שברים" -> new FractionAdditionSubtractionQuestion();//TODO לסדר איך התרגיל מוצג הסדר לא נכון
            case "חישוב זמן" -> new TimeProblemQuestion();//TODO לסדר איך התרגיל מוצג
            //case "חישוב הנחות" -> new DiscountQuestion();//TODO החישובים לא נכונים בכלל
            case "סדרות דינמיות" -> new DynamicSeriesQuestion();
            case "ניגזרות" -> new DerivativeQuestion();
            case "אינטגרלים" -> new IntegralQuestion();//TODO לא מחשב נכון בכלל
            case "חישוב מרחק בין שתי נקודות בגרף" -> new DistanceBetweenPointsQuestion();
            case "חישוב שיפוע" -> new SlopeBetweenPointsQuestion();
            case "נקודת חיתוך בין שני ישרים" -> new IntersectionPointQuestion();
            case "נקודות חיתוך בין פרבולות" -> new ParabolasIntersectionQuestion();//TODO לסדר את הסיפור הזה
            case "חיבור מספרים מרוכבים" -> new ComplexAdditionQuestion();
            case "חיסור מספרים מרוכבים" -> new ComplexSubtractionQuestion();
            case "כפל מספרים מרוכבים" -> new ComplexMultiplicationQuestion();
            case "חילוק מספרים מרוכבים" -> new ComplexDivisionQuestion(); //TODO ליבדוק שעובד
            case "חיבור מטריצות" -> new MatrixAdditionQuestion();//TODO לסדר את הסיפור הזה
            case "חיבור וקטורים" -> new VectorAdditionQuestion();
            case "חיסור וקטורים" -> new VectorSubtractionQuestion();
            case "מכפלה סקלרית של וקטורים" -> new VectorDotProductQuestion();
            //case "" -> new
            //case "" -> new
            //case "" -> new
            case "מישוואה עם נעלם אחד" -> new EquationWithOneUnknownQuestion();
            case "מערכת משוואות עם שני נעלמים" -> new EquationWithTwoUnknownsQuestion();
            case "שאלה מילולית" -> new WordProblemQuestion();
            case "משוואה ריבועית" -> new QuadraticEquationQuestion();
            default -> throw new IllegalArgumentException("תת-נושא לא נתמך: " + subTopic);
        };
    }
}