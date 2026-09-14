package progress.domain.testprogress;

import java.util.Collections;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.domain.Id;
import refactor.common.exception.DomainValidationException;
import refactor.progress.implementation.domain.markers.AnswerOption;
import refactor.progress.implementation.domain.markers.Question;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of = "questionId")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestAnswer {
    private Id<TestAnswer> id;

    private final Id<TestAttempt> testSubmissionId;
    private final Id<Question> questionId;

    @Getter(AccessLevel.NONE)
    private final Set<Id<AnswerOption>> selectedOptionsIds;

    public Set<Id<AnswerOption>> selectedOptionsIds() {
        return Collections.unmodifiableSet(selectedOptionsIds);
    }

    public static TestAnswer createNew(
            Id<TestAttempt> testSubmissionId, Id<Question> questionId, Set<Id<AnswerOption>> answerIds) {
        if (questionId == null || answerIds == null || testSubmissionId == null) {
            throw new DomainValidationException("Incorrect data to create test answer");
        }
        return new TestAnswer(null, testSubmissionId, questionId, answerIds);
    }
}
