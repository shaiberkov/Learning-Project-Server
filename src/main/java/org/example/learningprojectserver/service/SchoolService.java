package org.example.learningprojectserver.service;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import org.example.learningprojectserver.dto.SchoolDTO;
import org.example.learningprojectserver.entities.*;
import org.example.learningprojectserver.mappers.SchoolEntityToSchoolDTOMapper;
import org.example.learningprojectserver.repository.SchoolRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolEntityToSchoolDTOMapper schoolEntityToSchoolDTOMapper;


    @Autowired
    public SchoolService(SchoolRepository schoolRepository, SchoolEntityToSchoolDTOMapper schoolEntityToSchoolDTOMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolEntityToSchoolDTOMapper = schoolEntityToSchoolDTOMapper;
    }



    public BasicResponse getSchoolDTO(String SchoolManagerId){

        SchoolEntity schoolEntity=schoolRepository.findBySchoolManagerId(SchoolManagerId);
        SchoolDTO schoolDTO=schoolEntityToSchoolDTOMapper.apply(schoolEntity);
        BasicResponse basicResponse=new BasicResponse(true,null);
        basicResponse.setData(schoolDTO);
        return basicResponse;

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


    @PostConstruct
    public void init() {
        getAllClassesNameBySchoolCode("10");
    }

    public BasicResponse getAllClassesNameBySchoolCode(String schoolCode) {

         if (schoolCode == null ) {
             return new BasicResponse(false, "אין בית ספר כזה ");
         }
        SchoolEntity school = schoolRepository.findBySchoolCode(schoolCode);
        if (school == null) {
            return new BasicResponse(false, "בית ספר עם הקוד " + schoolCode + " לא נמצא");
        }
        List<String> classes = school.getClassRooms().stream().distinct().map(ClassRoomEntity::getName).toList();

        if (classes == null || classes.isEmpty()) {
            return new BasicResponse(false, "לא נמצאו כיתות בבית ספר זה");
        }

        BasicResponse response = new BasicResponse(true,null);
        response.setData(classes);
        return response;
    }

}
