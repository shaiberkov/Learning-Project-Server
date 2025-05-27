package org.example.learningprojectserver.mappers;


import org.example.learningprojectserver.dto.StudentTestStatusDTO;
import org.example.learningprojectserver.entities.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class StudentEntityToStudentTestStatusDTOMapper implements Mapper<StudentEntity, List<StudentTestStatusDTO>> {
    @Override
    public List<StudentTestStatusDTO> apply(StudentEntity student) {

//        List<StudentTestStatusDTO> studentTestStatusDTOList = new ArrayList<>();
//
//        for (TeacherTestEntity teacherTestEntity : student.getTeacherTests()) {
//            StudentTestStatusDTO studentTestStatusDTO = new StudentTestStatusDTO();
//            studentTestStatusDTO.setTestId(teacherTestEntity.getId());
//            studentTestStatusDTO.setSubject(teacherTestEntity.getSubject());
//            studentTestStatusDTO.setTopic(teacherTestEntity.getTopic());
//            Optional<Integer> optionalScore = student.getTeacherTestsResult().stream()
//                    .filter(result -> result.getTest().getId().equals(teacherTestEntity.getId()))
//                    .map(TeacherTestResultEntity::getScore)
//                    .filter(Objects::nonNull)
//                    .findFirst();
//
//            int score = optionalScore.orElse(-1);
//            studentTestStatusDTO.setScore(score);
//
//            studentTestStatusDTO.setStartTime(teacherTestEntity.getStartTime());
//            studentTestStatusDTO.setTimeLimitMinutes(teacherTestEntity.getTimeLimitMinutes());
//            studentTestStatusDTOList.add(studentTestStatusDTO);
//
//        }

        Map<Long, Integer> scoreMap = student.getTeacherTestsResult().stream()
                .collect(Collectors.toMap(
                        r -> r.getTest().getId(),
                        TeacherTestResultEntity::getScore,
                        (existing, ignore) -> existing
                ));

        List<StudentTestStatusDTO> studentTestStatusDTOList = student.getTeacherTests().stream()
                .map(test -> {
                    StudentTestStatusDTO dto = new StudentTestStatusDTO();
                    dto.setTestId(test.getId());
                    dto.setSubject(test.getSubject());
                    dto.setTopic(test.getTopic());

                    dto.setScore(scoreMap.getOrDefault(test.getId(), -1));

                    dto.setStartTime(test.getStartTime());
                    dto.setTimeLimitMinutes(test.getTimeLimitMinutes());
                    return dto;
                })
                .collect(Collectors.toList());


        return studentTestStatusDTOList;



    }


}
