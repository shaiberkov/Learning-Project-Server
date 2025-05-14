package org.example.learningprojectserver.service;

import org.example.learningprojectserver.dto.TestDTO;
import org.example.learningprojectserver.entities.TestEntity;
import org.example.learningprojectserver.entities.TestQuestionEntity;
import org.example.learningprojectserver.mappers.TestEntityToTestDTOMapper;
import org.example.learningprojectserver.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final TestEntityToTestDTOMapper testEntityToTestDTOMapper;
@Autowired
    public TestService(TestRepository testRepository, TestEntityToTestDTOMapper testEntityToTestDTOMapper) {
        this.testRepository = testRepository;
    this.testEntityToTestDTOMapper = testEntityToTestDTOMapper;
}


    public TestDTO getTestDto(Long testId) {
    TestEntity testEntity = testRepository.findTestByTestId(testId);
    List<TestQuestionEntity> testQuestionEntities=testEntity.getQuestions();
        Map<TestEntity, List<TestQuestionEntity>> testEntityListMap =new HashMap<>();
        testEntityListMap.put(testEntity, testQuestionEntities);
    return testEntityToTestDTOMapper.apply(testEntityListMap);

    }
}
