package refactor.course.domain.test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.domain.Id;
import refactor.common.exception.DomainValidationException;
import refactor.course.domain.test.valueobject.Option;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerOption {
    private Id<AnswerOption> id;
    private boolean isCorrect;
    private Option option;

    public static AnswerOption createNew(Option option, boolean isCorrect) {
        if (option == null) {
            throw new DomainValidationException("Incorrect data to create question option");
        }
        return new AnswerOption(null, isCorrect, option);
    }

    public static AnswerOption restore(Id<AnswerOption> id, Option option, boolean isCorrect) {
        return new AnswerOption(id, isCorrect, option);
    }
}
