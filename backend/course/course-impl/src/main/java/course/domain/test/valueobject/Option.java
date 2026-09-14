package course.domain.test.valueobject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Option {
    public static final int MAX_LENGTH = 100;

    private final String value;

    public static Option of(String option){
        if(option == null || option.isBlank() || option.length() > MAX_LENGTH){
            throw new DomainValidationException("Incorrect data to create option");
        }
        return new Option(option);
    }
}
