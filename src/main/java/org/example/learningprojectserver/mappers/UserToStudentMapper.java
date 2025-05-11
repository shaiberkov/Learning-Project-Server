package org.example.learningprojectserver.mappers;


import org.example.learningprojectserver.entities.SchoolManagerEntity;
import org.example.learningprojectserver.entities.StudentEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.enums.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserToStudentMapper implements Mapper<UserEntity, StudentEntity> {


    @Override
    public StudentEntity apply(UserEntity user) {
    StudentEntity studentEntity = new StudentEntity();
        studentEntity.setUsername(user.getUsername());
        studentEntity.setUserId(user.getUserId());
        studentEntity.setPassword(user.getPassword());
        studentEntity.setPasswordConfirm(user.getPasswordConfirm());
        studentEntity.setPasswordHash(user.getPasswordHash());
        studentEntity.setSalt(user.getSalt());
        studentEntity.setPhoneNumber(user.getPhoneNumber());
        studentEntity.setEmail(user.getEmail());
        studentEntity.setOtp(user.getOtp());
        studentEntity.setOtpTimestamp(user.getOtpTimestamp());
        studentEntity.setProfilePicture(user.getProfilePicture());
        studentEntity.setRole(Role.STUDENT);

        return studentEntity;
    }
}