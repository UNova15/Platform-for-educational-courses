package refactor.course.domain.internal.test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;
import refactor.course.domain.internal.test.valueobject.Option;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerOption {
    private Long id;
    private boolean isCorrect;
    private Option value;

    public static AnswerOption createNew(Option option, boolean isCorrect) {
        if (option == null) {
            throw new DomainValidationException("Incorrect data to create question option");
        }
        return new AnswerOption(null, isCorrect, option);
    }

    public static AnswerOption restore(Long id, Option option, boolean isCorrect) {
        return new AnswerOption(id, isCorrect, option);
    }
}
