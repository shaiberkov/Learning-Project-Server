package org.example.learningprojectserver.service.LanguageQuestion;

import org.example.learningprojectserver.service.SubjectQuestionGenerator;
import org.example.learningprojectserver.service.QuestionGenerator;
import org.example.learningprojectserver.entities.QuestionEntity;

public class LanguageQuestionFactory implements SubjectQuestionGenerator {

    @Override
    public QuestionGenerator getQuestionGenerator(String topic, String subTopic) {
//        return switch (subTopic.toLowerCase()) {
//            //case "תחביר" ->  new SyntaxQuestion();
////            case "הבנת הנקרא" -> new ReadingComprehensionQuestion();
//            default -> throw new IllegalArgumentException("תת-נושא לא נתמך: " + subTopic);
//        };
        return null;
    }
}
