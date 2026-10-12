package kira.course.domain.test.valueobject;

import common.exception.core.DomainValidationException;
import common.exception.core.LengthViolationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Option {
    public static final int MAX_LENGTH = 100;
    public static final int MIN_LENGTH = 1;

    private final String value;

    public static Option of(String option) {
        if (option == null) {
            throw new DomainValidationException("Empty data to create option");
        }

        if (option.length() <= MIN_LENGTH || option.length() > MAX_LENGTH) {
            throw new LengthViolationException("Incorrect option length", option.length(), MAX_LENGTH, MIN_LENGTH);
        }
        return new Option(option);
    }
}
