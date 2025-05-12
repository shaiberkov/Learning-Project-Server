package org.example.learningprojectserver.strategy.message;

import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.repository.UserRepository;

import java.util.List;

public class AllManagerStrategy implements MessageRecipientStrategy {
    private final UserRepository userRepository;

    public AllManagerStrategy(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getRecipients(String senderId) {
        return userRepository.getAllSchoolManagers();
    }
}
