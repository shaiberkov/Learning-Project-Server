package org.example.learningprojectserver.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

public class StudentEntity extends UserEntity {

    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProgressEntity userProgressEntity;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private QuestionHistoryEntity questionHistoryEntity;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TestResultEntity> userTestsResult;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<TestEntity> userTests;






    public StudentEntity() {}

}
