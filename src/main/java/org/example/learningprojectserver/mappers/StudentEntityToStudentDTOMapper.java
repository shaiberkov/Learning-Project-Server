package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.StudentDTO;
import org.example.learningprojectserver.entities.StudentEntity;
import org.springframework.stereotype.Component;

@Component
public class StudentEntityToStudentDTOMapper implements Mapper<StudentEntity, StudentDTO> {


    @Override
    public StudentDTO apply(StudentEntity student) {
        StudentDTO dto = new StudentDTO();
        dto.setFullName(student.getUsername());
        dto.setPhone(student.getPhoneNumber());
        dto.setClassName(student.getClassRoom().getName());
        dto.setGradeName(student.getClassRoom().getGrade().getGradeName());
        return dto;

    }
}
