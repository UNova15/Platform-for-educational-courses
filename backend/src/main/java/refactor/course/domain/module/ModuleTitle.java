package refactor.course.domain.module;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModuleTitle {
    public static final int MAX_MODULE_TITLE_SIZE = 100;

    private final String value;

    public static ModuleTitle of(String title) {
        if (title == null || title.isBlank() || title.length() > MAX_MODULE_TITLE_SIZE) {
            throw new DomainValidationException("Incorrect data to create module title");
        }
        return new ModuleTitle(title);
    }
}
