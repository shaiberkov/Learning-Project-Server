package org.example.learningprojectserver.strategy.message;


import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.repository.SchoolRepository;
import org.example.learningprojectserver.repository.UserRepository;

import java.util.List;

public class TeachersInGradeStrategy implements MessageRecipientStrategy {

    private final SchoolRepository schoolRepository;
    private final String grade;

    public TeachersInGradeStrategy( SchoolRepository schoolRepository, String grade) {
        this.schoolRepository = schoolRepository;
        this.grade = grade;
    }

    @Override
    public List<UserEntity> getRecipients(String senderId) {
        return schoolRepository.findTeachersByUserIdAndGradeName(senderId,grade);
    }
}