package org.example.learningprojectserver.service.QuestionGenerator;

import org.example.learningprojectserver.service.MathQuestion.MathQuestionFactory;
import org.example.learningprojectserver.service.LanguageQuestion.LanguageQuestionFactory;

public class QuestionGeneratorFactory {

    public static SubjectQuestionGenerator getSubjectQuestionGenerator(String subject) {
        switch (subject.toLowerCase()) {
            case "מתמטיקה":
                return new MathQuestionFactory();
            case "עברית":
                return new LanguageQuestionFactory();

            default:
                throw new IllegalArgumentException("מקצוע לא נתמך: " + subject);
        }
    }
}
