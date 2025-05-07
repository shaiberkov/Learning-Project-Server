package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.dto.QuestionDTO;
import org.example.learningprojectserver.entities.QuestionEntity;
import org.springframework.stereotype.Component;

@Component
public class QuestionEntityToQuestionDTOMapper  implements Mapper<QuestionEntity, QuestionDTO> {

    @Override
    public QuestionDTO apply(QuestionEntity question) {
         QuestionDTO dto=new QuestionDTO();
         dto.setId(question.getId());
         dto.setSubject(question.getSubject());
         dto.setTopic(question.getTopic());
         dto.setSubTopic(question.getSubTopic());
         dto.setQuestionText(question.getQuestionText());
         return dto;


    }
}
