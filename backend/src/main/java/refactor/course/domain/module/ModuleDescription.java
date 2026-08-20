package refactor.course.domain.module;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModuleDescription {
    public static final int MAX_MODULE_DESCRIPTION_SIZE = 500;

    private final String value;

    public static ModuleDescription of(String description) {
        if (description == null || description.isBlank()) {
            return new ModuleDescription("");
        }

        if (description.length() > MAX_MODULE_DESCRIPTION_SIZE) {
            throw new DomainValidationException(
                    "Length module description more than %d symbols".formatted(MAX_MODULE_DESCRIPTION_SIZE));
        }

        return new ModuleDescription(description);
    }
}
