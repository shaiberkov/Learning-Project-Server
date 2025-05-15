package org.example.learningprojectserver.dto;

import java.util.List;

public class TestDTO {
    private Long id;

    private List<QuestionDTO> questions;





    public TestDTO() {

    }

    public TestDTO(Long id, List<QuestionDTO> questions) {
        this.id = id;
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "TestDTO{" +
                "id=" + id +
                ", questions=" + questions +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
