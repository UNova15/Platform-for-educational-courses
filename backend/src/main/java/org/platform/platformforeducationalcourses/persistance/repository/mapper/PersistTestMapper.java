package org.platform.platformforeducationalcourses.persistance.repository.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import refactor.course.domain.option.AnswerOption;
import refactor.course.domain.question.Question;
import refactor.course.domain.test.Test;
import org.platform.platformforeducationalcourses.persistance.entity.course.AnswerOptionEntity;
import org.platform.platformforeducationalcourses.persistance.entity.course.QuestionEntity;
import org.platform.platformforeducationalcourses.persistance.entity.course.TestEntity;
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
                        toAnswerOption(entity.getAnswerOptions())))
                .collect(Collectors.toSet());
    }

    private Set<AnswerOption> toAnswerOption(Set<AnswerOptionEntity> answerOptionEntities) {
        return answerOptionEntities.stream()
                .map(entity -> AnswerOption.restore(
                        entity.getId(), entity.getQuestionId(), entity.getOption(), entity.isCorrect()))
                .collect(Collectors.toSet());
    }
}
