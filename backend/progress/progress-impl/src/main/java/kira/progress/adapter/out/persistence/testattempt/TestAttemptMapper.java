package kira.progress.adapter.out.persistence.testattempt;

import common.domain.Id;
import kira.progress.domain.markers.AnswerOption;
import kira.progress.domain.markers.Question;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.domain.testprogress.TestAnswer;
import kira.progress.domain.testprogress.TestAttempt;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TestAttemptMapper {

    public TestAttempt toDomain(TestAttemptEntity entity) {
        Id<TestAttempt> id = Id.of(entity.id());
        Id<User> userId = Id.of(entity.userId());
        Id<Test> testId = Id.of(entity.testId());

        Set<TestAnswer> answers = answersToDomain(id, entity.answers());

        return TestAttempt.restore(
                id, userId, testId, entity.startedAt(), entity.completedAt(), entity.score(), answers);
    }

    private Set<TestAnswer> answersToDomain(Id<TestAttempt> testAttemptId, Set<TestAnswerEntity> entities) {
        Map<Long, List<TestAnswerEntity>> answersByQuestionId =
                entities.stream().collect(Collectors.groupingBy(TestAnswerEntity::questionId));

        return answersByQuestionId.entrySet().stream()
                .map(entry -> {
                    Id<Question> questionId = Id.of(entry.getKey());

                    Set<Id<AnswerOption>> optionIds = entry.getValue().stream()
                            .map(attempt -> Id.<AnswerOption>of(attempt.optionId()))
                            .collect(Collectors.toSet());

                    return TestAnswer.restore(testAttemptId, questionId, optionIds);
                })
                .collect(Collectors.toSet());
    }

    public TestAttemptEntity toEntity(TestAttempt attempt) {

        Set<TestAnswerEntity> answerEntities = attempt.answers().stream()
                .map(this::toAnswerEntity)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        return new TestAttemptEntity(
                attempt.id().value(),
                attempt.userId().value(),
                attempt.testId().value(),
                attempt.startedAt(),
                attempt.completedAt(),
                attempt.score(),
                answerEntities);
    }

    private Set<TestAnswerEntity> toAnswerEntity(TestAnswer answer) {
        return answer.selectedOptionsIds().stream()
                .map(optionId -> new TestAnswerEntity(
                        optionId.value(), answer.questionId().value()))
                .collect(Collectors.toSet());
    }
}
