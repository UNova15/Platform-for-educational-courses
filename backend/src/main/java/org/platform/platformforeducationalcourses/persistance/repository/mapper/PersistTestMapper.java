package org.platform.platformforeducationalcourses.persistance.repository.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import refactor.course.domain.test.AnswerOption;
import refactor.course.domain.test.Question;
import refactor.course.domain.test.Test;
import refactor.course.adapter.out.persistance.test.AnswerOptionEntity;
import refactor.course.adapter.out.persistance.test.QuestionEntity;
import refactor.course.adapter.out.persistance.test.TestEntity;
import org.springframework.stereotype.Component;

@Component
public class PersistTestMapper {

    public List<TestEntity> toListEntities(List<Test> tests) {
        return tests.stream().map(TestEntity::fromTest).toList();
    }

    public List<Test> toListTests(Iterable<TestEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(testEntity -> Test.restore(
                        testEntity.getId(),
                        testEntity.getModuleId(),
                        testEntity.getDescription(),
                        testEntity.getOrderIndex(),
                        toQuestion(testEntity.getQuestions())))
                .toList();
    }

    private Set<Question> toQuestion(Set<QuestionEntity> questionEntities) {
        return questionEntities.stream()
                .map(entity -> Question.restore(
                        entity.getId(),
                        entity.getTestId(),
                        entity.getQuestion(),
                        entity.getOrderIndex(),
                        toAnswerOption(entity.answerOptions())))
                .collect(Collectors.toSet());
    }

    private Set<AnswerOption> toAnswerOption(Set<AnswerOptionEntity> answerOptionEntities) {
        return answerOptionEntities.stream()
                .map(entity -> AnswerOption.restore(
                        entity.getId(), entity.getQuestionId(), entity.getOption(), entity.isCorrect()))
                .collect(Collectors.toSet());
    }
}
