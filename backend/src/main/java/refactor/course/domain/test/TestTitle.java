package refactor.course.domain.test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestTitle {
    public static final int MAX_TEST_TITLE_SIZE = 100;

    private final String value;

    public static TestTitle of(String title) {
        if (title == null || title.isBlank() || title.length() > MAX_TEST_TITLE_SIZE) {
            throw new DomainValidationException("Incorrect data to create test title");
        }
        return new TestTitle(title);
    }
}
