package org.platform.platformforeducationalcourses.service.domain;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Question;
import org.platform.platformforeducationalcourses.domain.course.Test;
import org.platform.platformforeducationalcourses.dto.test.TestFindResponse;
import org.platform.platformforeducationalcourses.dto.test.*;
import org.platform.platformforeducationalcourses.creator.factory.TestFactory;
import org.platform.platformforeducationalcourses.mapper.TestMapper;
import org.platform.platformforeducationalcourses.repository.course.TestRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final TestMapper testMapper;
    private final TestFactory testFactory;

    public TestCreateResponse createTest(TestCreateDto dto, long moduleId) {
        Test test = testFactory.createTestFromCreateDto(dto, moduleId);

        Test savedTest = testRepository.save(test);

        return testMapper.toTestCreateResponse(savedTest);
    }

    //TODO exception
    public void deleteTest(long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow();

        testRepository.delete(test);
    }

    public void updateTest(TestUpdateDto updateDto) {
        Test test = testRepository.findById(updateDto.testId())
                .orElseThrow();
        Set<Question> questions = testFactory.createQuestionsFromUpdateDto(updateDto.questions(), test.getId());

        test.update(updateDto.description(), updateDto.orderIndex(), questions);
        testRepository.save(test);
    }

    //TODO добавить исключение
    public TestFindResponse getTest(long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow();

        return testMapper.toTestFindResponse(test);
    }
}
