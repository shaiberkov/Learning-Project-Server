package org.example.learningprojectserver.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("TEST")
public class TestQuestionEntity extends QuestionEntity {

    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;

    public TestQuestionEntity() {
        super();
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }
}
