package org.example.learningprojectserver.service;

import org.example.learningprojectserver.entities.SchoolEntity;
import org.example.learningprojectserver.entities.SchoolManagerEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.enums.Role;
import org.example.learningprojectserver.mappers.Mapper;
import org.example.learningprojectserver.mappers.UserMapperFactory;
import org.example.learningprojectserver.repository.SchoolRepository;
import org.example.learningprojectserver.repository.UserRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SystemAdminService {

private final UserRepository userRepository;

private final SchoolRepository schoolRepository;

private final UserMapperFactory userMapperFactory;
@Autowired
    public SystemAdminService(UserRepository userRepository, SchoolRepository schoolRepository, UserMapperFactory userMapperFactory) {
        this.userRepository = userRepository;
    this.schoolRepository = schoolRepository;
    this.userMapperFactory = userMapperFactory;
}

//    @PostConstruct()
//        public void init() {
//         assignUserAsSchoolManager("325256022");
//
//
//    }



    public BasicResponse assignUserAsSchoolManager(String userId) {
        UserEntity user = userRepository.findUserByUserId(userId);
        if (user == null) {
            return new BasicResponse(false, "יוזר לא קיים");
        }
        if(user.getRole()!=Role.STUDENT){
            return new BasicResponse(false, "המשתמש " + user.getUsername() + " משובץ כ־" + user.getRole());
        }
        Mapper<UserEntity,SchoolManagerEntity> mapper= userMapperFactory.getMapper(Role.SCHOOLMANAGER);
        SchoolManagerEntity schoolManager = mapper.apply(user);

        userRepository.delete(user);
        userRepository.save(schoolManager);

        return new BasicResponse(true, "המשתמש " + schoolManager.getUsername() + " שוייך בהצלחה כמנהל בית ספר");
    }


    public BasicResponse addNewSchoolToSystem(String schoolName,String schoolCode) {
        SchoolEntity school =schoolRepository.findBySchoolCode(schoolCode);
        if (school != null) {
        return new BasicResponse(false,"בית ספר עם קוד זה כבר קיים במערכת");
        }

        SchoolEntity newSchool = new SchoolEntity();
        newSchool.setSchoolCode(schoolCode);
        newSchool.setSchoolName(schoolName);
        schoolRepository.save(newSchool);
        return new BasicResponse(true,"בית ספר " +schoolName +" נוסף בהצלחה למערכת");
    }



    public BasicResponse assignSchoolManagerToSchool(String userId, String schoolCode) {
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        UserEntity user = userRepository.findUserByUserId(userId);

        if (user == null) {
            return new BasicResponse(false, "המשתמש לא נמצא");
        }

        if ((user.getRole() != (Role.SCHOOLMANAGER))) {
            return new BasicResponse(false, "המשתמש אינו מנהל בית ספר");
        }

        if (school == null) {
            return new BasicResponse(false, "בית הספר לא נמצא");
        }
        if(school.getSchoolManager()!=null){
            return new BasicResponse(false, "כבר יש מנהל לבית ספר " + school.getSchoolName());
        }

        SchoolManagerEntity schoolManager = (SchoolManagerEntity) user;

        if (schoolManager.getSchool()!=null){

            return new BasicResponse(false,"מנהל זה כבר שוייך לבית ספר");
        }

        schoolManager.setSchool(school);
        school.setSchoolManager(schoolManager);

        userRepository.save(schoolManager);
        schoolRepository.save(school);

        return new BasicResponse(true, "המשתמש " + schoolManager.getUsername() + " מונה בהצלחה כמנהל של בית הספר " + school.getSchoolName());
    }
    //הוספת סתטיסטיקה בהמשך

    public BasicResponse removeSchoolManagerFromSchool(String userId) {
        UserEntity user = userRepository.findUserByUserId(userId);

        if (user == null || user.getRole() != Role.SCHOOLMANAGER) {
            return new BasicResponse(false, "המשתמש אינו מנהל בית ספר או לא קיים");
        }

        SchoolManagerEntity schoolManager = (SchoolManagerEntity) user;
        SchoolEntity school = schoolManager.getSchool();

        if (school == null) {
            return new BasicResponse(false, "מנהל זה אינו משויך לשום בית ספר");
        }

        schoolManager.setSchool(null);
        school.setSchoolManager(null);

        userRepository.save(schoolManager);
        schoolRepository.save(school);

        return new BasicResponse(true, "המנהל " + schoolManager.getUsername() + " הוסר בהצלחה מהשיוך לבית הספר " + school.getSchoolName());
    }



}
