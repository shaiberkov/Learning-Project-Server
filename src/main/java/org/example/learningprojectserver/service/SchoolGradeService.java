package org.example.learningprojectserver.service;

import org.example.learningprojectserver.entities.ClassRoomEntity;
import org.example.learningprojectserver.repository.ClassRoomRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolGradeService {

  private final ClassRoomRepository classRoomRepository;
@Autowired
     public SchoolGradeService(ClassRoomRepository classRoomRepository) {
    this.classRoomRepository = classRoomRepository;
}

    public BasicResponse getClassNamesBySchoolAndGrade(String schoolCode, String gradeName) {
        if (schoolCode == null || schoolCode.isEmpty()) {
            return new BasicResponse(false, "קוד בית הספר לא יכול להיות ריק");
        }

        if (gradeName == null || gradeName.isEmpty()) {
            return new BasicResponse(false, "שם השכבה לא יכול להיות ריק");
        }

        List<ClassRoomEntity> classRooms = classRoomRepository.findClassRoomsBySchoolCodeAndGradeName(schoolCode, gradeName);

        if (classRooms.isEmpty()) {
            return new BasicResponse(false, "לא נמצאו כיתות לשכבה זו ");
        }

        List<String> classNames = classRooms.stream()
                .map(ClassRoomEntity::getName)
                .toList();

        BasicResponse basicResponse = new BasicResponse(true, "הכיתות לשכבה " + gradeName + " בבית הספר " + schoolCode + " הן:");
        basicResponse.setData(classNames);

        return basicResponse;
    }


    }

