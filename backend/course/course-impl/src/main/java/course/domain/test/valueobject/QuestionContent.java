package course.domain.test.valueobject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionContent {
    public static final int MAX_QUESTION_LENGTH = 100;

    private final String value;

    public static QuestionContent of(String question) {
        if (question == null || question.isBlank() || question.length() > MAX_QUESTION_LENGTH) {
            throw new DomainValidationException("Incorrect data to create question title");
        }
        return new QuestionContent(question);
    }
}
