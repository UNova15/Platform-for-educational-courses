package refactor.course.domain.test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestDescription {
    public static final int MAX_TEST_DESCRIPTION_LENGTH = 500;

    private String value;

    public static TestDescription of(String description) {
        if (description == null || description.isBlank()) {
            return new TestDescription("");
        }

        if (description.length() > MAX_TEST_DESCRIPTION_LENGTH) {
            throw new DomainValidationException(
                    "Length test description more than %d symbols".formatted(MAX_TEST_DESCRIPTION_LENGTH));
        }

        return new TestDescription(description);
    }
}
