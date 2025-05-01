package org.example.learningprojectserver.mappers;


import org.example.learningprojectserver.entities.SchoolEntity;
import org.example.learningprojectserver.entities.TeacherEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.enums.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserToTeacherMapper implements EntityMapper<TeacherEntity> {

    private SchoolEntity school;

    @Override
    public EntityMapper<TeacherEntity> setSchool(SchoolEntity school) {
        this.school = school;
        return this;
    }

    @Override
    public TeacherEntity map(UserEntity user) {
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
        teacher.setSessionList(new ArrayList<>());
        teacher.setStudents(new ArrayList<>());
        teacher.setTeachingClassRooms(new ArrayList<>());
        teacher.setTeachingSubjects(new ArrayList<>());
        teacher.setTeachingSchool(school);

        return teacher;
    }
}