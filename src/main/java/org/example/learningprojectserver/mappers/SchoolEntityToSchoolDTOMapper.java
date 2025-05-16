package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.SchoolDTO;
import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.entities.SchoolEntity;
import org.example.learningprojectserver.entities.TestQuestionEntity;
import org.springframework.stereotype.Component;

@Component
public class SchoolEntityToSchoolDTOMapper implements Mapper<SchoolEntity, SchoolDTO> {
    @Override
    public SchoolDTO apply(SchoolEntity school) {
        SchoolDTO schoolDTO = new SchoolDTO();
        schoolDTO.setSchoolName(school.getSchoolName());
        schoolDTO.setSchoolCode(school.getSchoolCode());
        schoolDTO.setTeacherCount(school.getTeachers().size());
        schoolDTO.setStudentCount(school.getStudents().size());
        return schoolDTO;
    }
}
