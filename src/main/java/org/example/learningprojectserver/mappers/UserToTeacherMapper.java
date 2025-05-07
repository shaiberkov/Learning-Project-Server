package org.example.learningprojectserver.mappers;


import org.example.learningprojectserver.dto.MessageDTO;
import org.example.learningprojectserver.entities.MessageEntity;
import org.example.learningprojectserver.entities.SchoolEntity;
import org.example.learningprojectserver.entities.TeacherEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.enums.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserToTeacherMapper implements Mapper<UserEntity, TeacherEntity> {


    @Override
    public TeacherEntity apply(UserEntity user) {
        TeacherEntity teacher = new TeacherEntity();

        teacher.setUserId(user.getUserId());
        teacher.setUsername(user.getUsername());
        teacher.setPassword(user.getPassword());
        teacher.setPasswordConfirm(user.getPasswordConfirm());
        teacher.setPasswordHash(user.getPasswordHash());
        teacher.setSalt(user.getSalt());
        teacher.setPhoneNumber(user.getPhoneNumber());
        teacher.setEmail(user.getEmail());
        teacher.setOtp(user.getOtp());
        teacher.setOtpTimestamp(user.getOtpTimestamp());
        teacher.setProfilePicture(user.getProfilePicture());
        teacher.setRole(Role.TEACHER);
        return teacher;
    }
}