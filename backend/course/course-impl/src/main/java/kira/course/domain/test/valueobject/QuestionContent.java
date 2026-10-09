package kira.course.domain.test.valueobject;

import common.exception.core.DomainValidationException;
import common.exception.named.LengthViolationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionContent {
    public static final int MAX_LENGTH = 100;
    public static final int MIN_LENGTH = 1;

    private final String value;

    public static QuestionContent of(String question) {
        if (question == null) {
            throw new DomainValidationException("Empty data to create question title");
        }

        if (question.length() <= MIN_LENGTH || question.length() > MAX_LENGTH) {
            throw new LengthViolationException(
                    "Incorrect question content length", "QuestionContent", question.length(), MAX_LENGTH, MIN_LENGTH);
        }

        return new QuestionContent(question);
    }
}
