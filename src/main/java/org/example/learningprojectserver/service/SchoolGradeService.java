package org.example.learningprojectserver.service;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.entities.ClassRoomEntity;
import org.example.learningprojectserver.repository.ClassRoomRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolGradeService {

  private final ClassRoomRepository classRoomRepository;

    public BasicResponse getClassNamesBySchoolAndGrade(String schoolCode, String gradeName) {
        if (schoolCode == null || schoolCode.isEmpty()) {
            return new BasicResponse(false, "קוד בית הספר לא יכול להיות ריק");
        }

        if (gradeName == null || gradeName.isEmpty()) {
            return new BasicResponse(false, "שם השכבה לא יכול להיות ריק");
        }


        List<String> classNames = classRoomRepository.findClassRoomNamesBySchoolCodeAndGradeName(schoolCode, gradeName);

        if (classNames.isEmpty()) {
            return new BasicResponse(false, "לא נמצאו כיתות לשכבה זו");
        }

        BasicResponse basicResponse = new BasicResponse(true, "הכיתות לשכבה " + gradeName + " בבית הספר " + schoolCode + " הן:");
        basicResponse.setData(classNames);

        return basicResponse;
    }


    }

