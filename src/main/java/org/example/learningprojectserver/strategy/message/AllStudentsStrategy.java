package org.example.learningprojectserver.strategy.message;


import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.repository.SchoolRepository;
import org.example.learningprojectserver.repository.UserRepository;

import java.util.List;

public class AllStudentsStrategy implements MessageRecipientStrategy {

    private final SchoolRepository schoolRepository;

    public AllStudentsStrategy(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }


    @Override
    public List<UserEntity> getRecipients(String senderId) {
        return schoolRepository.findStudentsByUserId(senderId);
    }
}