package refactor.progress.domain.testprogress.valueobject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import refactor.common.domain.Id;
import refactor.common.exception.DomainValidationException;
import refactor.progress.domain.markers.AnswerOption;
import refactor.progress.domain.markers.Question;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.testprogress.TestAnswer;

import java.util.Map;
import java.util.Set;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerKey {
    private final Id<Test> testId;

    @Getter(AccessLevel.NONE)
    private final Map<Id<Question>, Set<Id<AnswerOption>>> key;

    public static AnswerKey createNew(Id<Test> testId, Map<Id<Question>, Set<Id<AnswerOption>>> keys) {
        if (testId == null || keys == null || keys.isEmpty()) {
            throw new DomainValidationException("Questions and Answer options cannot be empty");
        }

        return new AnswerKey(testId, keys);
    }

    //в случае если будет указан id ответа от другого теста то он не будет засчитан (ошибки не будет)
    public int calculateGrade(Set<TestAnswer> answers) {
        int score = 0;

        for (var answer : answers) {

            if (!key.containsKey(answer.questionId())) {
                throw new DomainValidationException("Answer contains invalid question id");
            }

            Set<Id<AnswerOption>> answersIds = answer.selectedOptionsIds();
            Set<Id<AnswerOption>> correctAnswersIds = key.get(answer.questionId());

            if(correctAnswersIds.equals(answersIds)){
                score++;
            }

        }

        return score;
    }
}
