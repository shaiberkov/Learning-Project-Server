package org.example.learningprojectserver.service;

import jakarta.persistence.Entity;
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
        // בדיקה אם הקוד ריק או null
        if (schoolCode == null || schoolCode.isEmpty()) {
            return new BasicResponse(false, "קוד בית הספר לא יכול להיות ריק");
        }

        // ניסיון למצוא את בית הספר לפי הקוד
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית ספר עם הקוד " + schoolCode + " לא נמצא");
        }

        // שליפת רשימת השכבות מבית הספר
        List<SchoolGradeEntity> grades = school.getSchoolGrades();
        if (grades == null || grades.isEmpty()) {
            return new BasicResponse(false, "לא נמצאו שכבות בבית ספר זה");
        }

        // מיפוי לשמות שכבות
        List<String> gradeNames = grades.stream()
                .map(SchoolGradeEntity::getGradeName)
                .toList();

        // יצירת ריספונס עם הנתונים
        BasicResponse response = new BasicResponse(true,null);
        response.setData(gradeNames);
        return response;
    }

}
