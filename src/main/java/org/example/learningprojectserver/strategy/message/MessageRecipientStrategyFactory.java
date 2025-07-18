package org.example.learningprojectserver.strategy.message;


import org.example.learningprojectserver.repository.SchoolRepository;
import org.example.learningprojectserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageRecipientStrategyFactory {

    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageRecipientStrategyFactory(SchoolRepository schoolRepository, UserRepository userRepository) {
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
    }




    public MessageRecipientStrategy getStrategy(String recipientType, String recipientValue) {
        return switch (recipientType.toLowerCase()) {
            case "כיתה" -> new ClassRecipientStrategy(schoolRepository, recipientValue);
            case "שכבה" -> new GradeRecipientStrategy(schoolRepository, recipientValue);
            case "מורי-שכבה" -> new TeachersInGradeStrategy(schoolRepository, recipientValue);
            case "כל-המורים" -> new AllTeachersStrategy(schoolRepository);
            case "כל-התלמידים" -> new AllStudentsStrategy(schoolRepository);
            case "כל מנהלי בית ספר"-> new AllManagerStrategy(userRepository);
            default -> throw new IllegalArgumentException("סוג יעד לא חוקי: " + recipientType);
        };
    }
}