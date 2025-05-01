package org.example.learningprojectserver.strategy.message;


import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.repository.SchoolRepository;
import org.example.learningprojectserver.repository.UserRepository;

import java.util.List;

public class ClassRecipientStrategy implements MessageRecipientStrategy {

    private final SchoolRepository schoolRepository;
    private final String className;

    public ClassRecipientStrategy(SchoolRepository schoolRepository, String className) {
        this.schoolRepository = schoolRepository;
        this.className = className;
    }

    @Override
    public List<UserEntity> getRecipients(String senderId) {
        return schoolRepository.findClassRoomStudentsByUserIdAndClassName(className,senderId);
    }
}