package org.example.learningprojectserver.service;

import jakarta.persistence.Entity;
import org.example.learningprojectserver.entities.ClassRoomEntity;
import org.example.learningprojectserver.entities.SchoolEntity;
import org.example.learningprojectserver.entities.SchoolGradeEntity;
import org.example.learningprojectserver.repository.SchoolRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;
    @Autowired
    public SchoolService(SchoolRepository schoolRepository) {

        this.schoolRepository = schoolRepository;
    }
    public BasicResponse getGradesBySchoolCode(String schoolCode) {
        if (schoolCode == null || schoolCode.isEmpty()) {
            return new BasicResponse(false, "קוד בית הספר לא יכול להיות ריק");
        }

        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית ספר עם הקוד " + schoolCode + " לא נמצא");
        }

        List<SchoolGradeEntity> grades = school.getSchoolGrades();
        if (grades == null || grades.isEmpty()) {
            return new BasicResponse(false, "לא נמצאו שכבות בבית ספר זה");
        }

        List<String> gradeNames = grades.stream()
                .map(SchoolGradeEntity::getGradeName)
                .toList();

        BasicResponse response = new BasicResponse(true,null);
        response.setData(gradeNames);
        return response;
    }

    public BasicResponse getAllClassesNameBySchoolCode(String schoolCode) {

         if (schoolCode == null ) {
             return new BasicResponse(false, "אין בית ספר כזה ");
         }
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית ספר עם הקוד " + schoolCode + " לא נמצא");
        }
        List<String> classes = school.getClassRooms().stream().map(ClassRoomEntity::getName).toList();
        if (classes == null || classes.isEmpty()) {
            return new BasicResponse(false, "לא נמצאו כיתות בבית ספר זה");
        }
        BasicResponse response = new BasicResponse(true,null);
        response.setData(classes);
        return response;
    }

}
