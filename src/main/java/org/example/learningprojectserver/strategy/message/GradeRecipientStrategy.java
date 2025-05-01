package org.example.learningprojectserver.strategy.message;


import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.repository.SchoolRepository;
import org.example.learningprojectserver.repository.UserRepository;

import java.util.List;

public class GradeRecipientStrategy implements MessageRecipientStrategy {

    private final SchoolRepository schoolRepository;
    private final String grade;

    public GradeRecipientStrategy(SchoolRepository schoolRepository, String grade) {
        this.schoolRepository = schoolRepository;
        this.grade = grade;
    }

    @Override
    public List<UserEntity> getRecipients(String senderId) {
        return schoolRepository.findStudentsByUserIdAndGradeName(senderId,grade);
    }
}