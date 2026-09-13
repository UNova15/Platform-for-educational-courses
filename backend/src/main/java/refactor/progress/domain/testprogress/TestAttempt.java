package refactor.progress.domain.testprogress;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.domain.Id;
import refactor.common.exception.DomainValidationException;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;
import refactor.progress.domain.testprogress.valueobject.AnswerKey;
import refactor.progress.domain.testprogress.valueobject.SubmittedAnswers;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestAttempt {
    private Id<TestAttempt> id;

    private final Id<User> userId;
    private final Id<Test> testId;
    private final LocalDateTime startedAt;
    private LocalDateTime completedAt;

    // В данный момент счет вычисляется как 1 балл за 1 правильно выполненный вопрос. В дальнейшем в самих вопросах
    // будет хранится количество баллов за него
    private int score;

    @Getter(AccessLevel.NONE)
    private Set<TestAnswer> answers;

    public Set<TestAnswer> answers() {
        return Collections.unmodifiableSet(answers);
    }

    public static TestAttempt createNew(Id<User> userId, Id<Test> testId) {
        if (userId == null || testId == null) {
            throw new DomainValidationException("Incorrect data to create test submission");
        }

        return new TestAttempt(null, userId, testId, LocalDateTime.now(), null, 0, Set.of());
    }

    public boolean isCompleted(){
        return completedAt != null;
    }

    public void submitAnswers(Set<SubmittedAnswers> usersAnswers, AnswerKey key) {
        if (completedAt != null) {
            throw new DomainValidationException("Attempt has already been saved");
        }

        if (usersAnswers == null || key == null || !key.testId().equals(testId)) {
            throw new DomainValidationException("Invalid answer or key to submit test attempt");
        }

        Set<TestAnswer> answers = usersAnswers.stream()
                .map(answer -> TestAnswer.createNew(this.id, answer.questionId(), answer.optionsIds()))
                .collect(Collectors.toSet());

        int score = key.calculateGrade(answers);

        this.completedAt = LocalDateTime.now();
        this.score = score;
        this.answers = answers;
    }
}
