package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.dto.UserTestStatusDTO;
import org.example.learningprojectserver.entities.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserEntityToUserTestStatusDTOMapper implements Mapper<StudentEntity, List<UserTestStatusDTO>>{
    @Override
    public List<UserTestStatusDTO> apply(StudentEntity student) {

        List<UserTestStatusDTO> userTestStatusDTOList = new ArrayList<>();

        for (TeacherTestEntity teacherTestEntity : student.getTeacherTests()) {
            UserTestStatusDTO userTestStatusDTO = new UserTestStatusDTO();
            userTestStatusDTO.setTestId(teacherTestEntity.getId());
            userTestStatusDTO.setSubject(teacherTestEntity.getSubject());
            userTestStatusDTO.setTopic(teacherTestEntity.getTopic());
            Optional<Integer> optionalScore = student.getTeacherTestsResult().stream()
                    .filter(result -> result.getTest().getId().equals(teacherTestEntity.getId()))
                    .map(TeacherTestResultEntity::getScore)
                    .filter(Objects::nonNull)
                    .findFirst();

            int score = optionalScore.orElse(-1);
            userTestStatusDTO.setScore(score);

            userTestStatusDTO.setStartTime(teacherTestEntity.getStartTime());
            userTestStatusDTO.setTimeLimitMinutes(teacherTestEntity.getTimeLimitMinutes());
            userTestStatusDTOList.add(userTestStatusDTO);

        }


return userTestStatusDTOList;



    }


}
