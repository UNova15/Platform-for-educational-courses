package kira.progress.domain.testprogress;

import common.domain.entity.Id;
import common.domain.exception.DomainValidationException;
import java.util.Collections;
import java.util.Set;
import kira.progress.domain.ProgressAggregateType;
import kira.progress.domain.markers.AnswerOption;
import kira.progress.domain.markers.Question;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestAnswer {
    private final Id<Question> questionId;

    @Getter(AccessLevel.NONE)
    private final Set<Id<AnswerOption>> selectedOptionsIds;

    public Set<Id<AnswerOption>> selectedOptionsIds() {
        return Collections.unmodifiableSet(selectedOptionsIds);
    }

    public static TestAnswer create(Id<Question> questionId, Set<Id<AnswerOption>> answerIds) {
        if (questionId == null || answerIds == null) {
            throw new DomainValidationException(
                    "Incorrect data to create test answer", ProgressAggregateType.TEST_ANSWER);
        }
        return new TestAnswer(questionId, answerIds);
    }

    public static TestAnswer restore(Id<Question> questionId, Set<Id<AnswerOption>> selectedOptionsIds) {
        return new TestAnswer(questionId, selectedOptionsIds);
    }
}
