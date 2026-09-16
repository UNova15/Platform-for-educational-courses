package kira.progress.domain.testprogress;

import java.util.Collections;
import java.util.Set;

import common.domain.Id;
import common.exception.DomainValidationException;
import kira.progress.domain.markers.AnswerOption;
import kira.progress.domain.markers.Question;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

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
