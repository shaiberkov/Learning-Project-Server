package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.MessageDTO;
import org.example.learningprojectserver.dto.QuestionDTO;
import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.entities.MessageEntity;
import org.example.learningprojectserver.entities.TestEntity;
import org.example.learningprojectserver.entities.TestQuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TestEntityToTestDTOMapper implements Mapper<Map<TestEntity, List<TestQuestionEntity>>, TestDTO>{

private final QuestionEntityToQuestionDTOMapper questionEntityToQuestionDTOMapper;
@Autowired
    public TestEntityToTestDTOMapper(QuestionEntityToQuestionDTOMapper questionEntityToQuestionDTOMapper) {
        this.questionEntityToQuestionDTOMapper = questionEntityToQuestionDTOMapper;
    }

    @Override
    public TestDTO apply(Map<TestEntity, List<TestQuestionEntity>> questionsInTest) {
        Map.Entry<TestEntity, List<TestQuestionEntity>> entry = questionsInTest.entrySet().iterator().next();

        TestEntity testEntity = entry.getKey();
        List<TestQuestionEntity> questions = entry.getValue();
        Long id=testEntity.getId();
        List<QuestionDTO> questionDTOs = questions.stream()
                .map(questionEntityToQuestionDTOMapper).toList();
        return new TestDTO(id,questionDTOs);

    }


}
