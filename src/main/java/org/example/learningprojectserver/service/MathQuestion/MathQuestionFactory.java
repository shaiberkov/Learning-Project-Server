package org.example.learningprojectserver.service.MathQuestion;
import org.example.learningprojectserver.service.MathQuestion.AnalyticGeometry.DistanceBetweenPointsQuestion;
import org.example.learningprojectserver.service.MathQuestion.AnalyticGeometry.IntersectionPointQuestion;
import org.example.learningprojectserver.service.MathQuestion.AnalyticGeometry.ParabolasIntersectionQuestion;
import org.example.learningprojectserver.service.MathQuestion.AnalyticGeometry.SlopeBetweenPointsQuestion;
import org.example.learningprojectserver.service.MathQuestion.ComplexNumbers.ComplexAdditionQuestion;
import org.example.learningprojectserver.service.MathQuestion.ComplexNumbers.ComplexDivisionQuestion;
import org.example.learningprojectserver.service.MathQuestion.ComplexNumbers.ComplexMultiplicationQuestion;
import org.example.learningprojectserver.service.MathQuestion.ComplexNumbers.ComplexSubtractionQuestion;
import org.example.learningprojectserver.service.MathQuestion.Equations.EquationWithOneUnknownQuestion;
import org.example.learningprojectserver.service.MathQuestion.Equations.EquationWithTwoUnknownsQuestion;
import org.example.learningprojectserver.service.MathQuestion.Equations.QuadraticEquationQuestion;
import org.example.learningprojectserver.service.MathQuestion.Fractions.FractionAdditionQuestion;
import org.example.learningprojectserver.service.MathQuestion.Fractions.FractionAdditionSubtractionQuestion;
import org.example.learningprojectserver.service.MathQuestion.Fractions.FractionMultiplicationQuestion;
import org.example.learningprojectserver.service.MathQuestion.Fractions.FractionSubtractionQuestion;
import org.example.learningprojectserver.service.MathQuestion.Integers.*;
import org.example.learningprojectserver.service.MathQuestion.IntegralAndDifferentialCalculus.DerivativeQuestion;
import org.example.learningprojectserver.service.MathQuestion.IntegralAndDifferentialCalculus.IntegralQuestion;
import org.example.learningprojectserver.service.MathQuestion.Matrix.MatrixAdditionQuestion;
import org.example.learningprojectserver.service.MathQuestion.Vectors.VectorAdditionQuestion;
import org.example.learningprojectserver.service.MathQuestion.Vectors.VectorDotProductQuestion;
import org.example.learningprojectserver.service.MathQuestion.Vectors.VectorSubtractionQuestion;
import org.example.learningprojectserver.service.MathQuestion.VerbalQuestions.DiscountQuestion;
import org.example.learningprojectserver.service.MathQuestion.VerbalQuestions.TimeProblemQuestion;
import org.example.learningprojectserver.service.MathQuestion.VerbalQuestions.WordProblemQuestion;
import org.example.learningprojectserver.service.SubjectQuestionGenerator;
import org.example.learningprojectserver.service.QuestionGenerator;

public class MathQuestionFactory implements SubjectQuestionGenerator {

    @Override
    public QuestionGenerator getQuestionGenerator(String topic, String subTopic) {
        return switch (subTopic.toLowerCase()) {
            case "חיבור" -> new AdditionQuestion();
            case "חיסור" -> new SubtractionQuestion();
            case "כפל" -> new MultiplicationQuestion();
            case "חילוק" -> new DivisionQuestion();
            case "חזקות" -> new ExponentiationQuestion();
            case "שורש ריבועי" -> new SquareRootQuestion();
            case "חיבור שברים" -> new FractionAdditionQuestion();
            case "חיסור שברים" -> new FractionSubtractionQuestion();
            case "כפל שברים" -> new FractionMultiplicationQuestion();
            case "חיבור וחיסור שברים" -> new FractionAdditionSubtractionQuestion();//todo לסדר את זה
            case "חיבור מספרים מרוכבים" -> new ComplexAdditionQuestion();
            case "חיסור מספרים מרוכבים" -> new ComplexSubtractionQuestion();
            case "כפל מספרים מרוכבים" -> new ComplexMultiplicationQuestion();
            case "חילוק מספרים מרוכבים" -> new ComplexDivisionQuestion(); //TODO ליבדוק שעובד
            case "חיבור וקטורים" -> new VectorAdditionQuestion();
            case "חיסור וקטורים" -> new VectorSubtractionQuestion();
            case "מכפלה סקלרית של וקטורים" -> new VectorDotProductQuestion();
            case "חישוב מרחק בין שתי נקודות בגרף" -> new DistanceBetweenPointsQuestion();
            case "חישוב שיפוע" -> new SlopeBetweenPointsQuestion();
            case "נקודת חיתוך בין שני ישרים" -> new IntersectionPointQuestion();
            case "נקודות חיתוך בין פרבולות" -> new ParabolasIntersectionQuestion();//TODO לסדר את הסיפור הזה
            case "מישוואה עם נעלם אחד" -> new EquationWithOneUnknownQuestion();
            case "מערכת משוואות עם שני נעלמים" -> new EquationWithTwoUnknownsQuestion();
            case "משוואה ריבועית" -> new QuadraticEquationQuestion();
            case "חיבור מטריצות" -> new MatrixAdditionQuestion();//TODO לסדר את הסיפור הזה
            case "שאלה מילולית" -> new WordProblemQuestion();
            case "חישוב זמן" -> new TimeProblemQuestion();//TODO לסדר איך התרגיל מוצג
            case "חישוב הנחות" -> new DiscountQuestion();//TODO החישובים לא נכונים בכלל
            case "ניגזרות" -> new DerivativeQuestion();
            case "אינטגרלים" -> new IntegralQuestion();//TODO לא מחשב נכון בכלל
            case "סדרות דינמיות" -> new DynamicSeriesQuestion();
            default -> throw new IllegalArgumentException("תת-נושא לא נתמך: " + subTopic);
        };
    }
}
