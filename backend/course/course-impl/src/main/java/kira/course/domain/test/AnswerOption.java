package kira.course.domain.test;

import common.domain.Id;
import common.exception.DomainValidationException;
import kira.course.domain.test.valueobject.Option;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

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
