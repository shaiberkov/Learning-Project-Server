package org.example.learningprojectserver.service;

import org.example.learningprojectserver.entities.SchoolEntity;
import org.example.learningprojectserver.entities.SchoolManagerEntity;
import org.example.learningprojectserver.entities.TeacherEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.repository.SchoolManagerRepository;
import org.example.learningprojectserver.repository.SchoolRepository;
import org.example.learningprojectserver.repository.UserRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SchoolManagerService {
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    @Autowired
    public SchoolManagerService(UserRepository userRepository, SchoolRepository schoolRepository) {
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
    }



    public BasicResponse assignUserAsSchoolTeacher(String userId, String schoolCode) {
        UserEntity user = userRepository.findUserByUserId(userId);

        if (user == null) {
            return new BasicResponse(false, "יוזר לא נמצא במערכת");
        }

        if (user.getRole() != Role.STUDENT) {
            return new BasicResponse(false, "המשתמש " + user.getUsername() + " משובץ כ־" + user.getRole());
        }

        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית ספר לא קיים במערכת");
        }

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

        school.getTeachers().add(teacher);

        userRepository.delete(user);
        userRepository.save(teacher);
        schoolRepository.save(school);

        return new BasicResponse(true, "המורה " + teacher.getUsername() + " שויך בהצלחה לבית הספר " + school.getSchoolName());
    }


    public BasicResponse removeTeacherFromSchool(String userId) {
        UserEntity user = userRepository.findUserByUserId(userId);

        if (user == null) {
            return new BasicResponse(false, "המשתמש לא נמצא במערכת");
        }

        if (user.getRole() != Role.TEACHER) {
            return new BasicResponse(false, "המשתמש " + user.getUsername() + " אינו מורה");
        }

        TeacherEntity teacher = (TeacherEntity) user;
        SchoolEntity school = teacher.getTeachingSchool();

        if (school == null) {
            return new BasicResponse(false, "המורה " + teacher.getUsername() + " אינו משויך לאף בית ספר");
        }

        school.getTeachers().remove(teacher);
        teacher.setTeachingSchool(null);

        userRepository.save(teacher);
        schoolRepository.save(school);

        return new BasicResponse(true, "המורה " + teacher.getUsername() + " הוסר בהצלחה מהשיוך לבית הספר " + school.getSchoolName());
    }


}
