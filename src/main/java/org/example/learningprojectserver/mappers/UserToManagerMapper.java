package org.example.learningprojectserver.mappers;


import org.example.learningprojectserver.entities.SchoolManagerEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.enums.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserToManagerMapper implements EntityMapper<SchoolManagerEntity> {

    @Override
    public SchoolManagerEntity map(UserEntity user) {
        SchoolManagerEntity schoolManager = new SchoolManagerEntity();

        schoolManager.setUsername(user.getUsername());
        schoolManager.setUserId(user.getUserId());
        schoolManager.setPassword(user.getPassword());
        schoolManager.setPasswordConfirm(user.getPasswordConfirm());
        schoolManager.setPasswordHash(user.getPasswordHash());
        schoolManager.setSalt(user.getSalt());
        schoolManager.setPhoneNumber(user.getPhoneNumber());
        schoolManager.setEmail(user.getEmail());
        schoolManager.setOtp(user.getOtp());
        schoolManager.setOtpTimestamp(user.getOtpTimestamp());
        schoolManager.setProfilePicture(user.getProfilePicture());
        schoolManager.setRole(Role.SCHOOLMANAGER);
        schoolManager.setSessionList(new ArrayList<>());

        return schoolManager;
    }
}