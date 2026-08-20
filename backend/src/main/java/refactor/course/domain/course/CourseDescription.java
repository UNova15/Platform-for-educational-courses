package refactor.course.domain.course;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseDescription {
    public static final int MAX_COURSE_DESCRIPTION_SIZE = 500;

    private final String value;

    public static CourseDescription of(String description) {
        if (description == null || description.isBlank()) {
            return new CourseDescription("");
        }

        if (description.length() > MAX_COURSE_DESCRIPTION_SIZE) {
            throw new DomainValidationException(
                    "Length course description more than %d symbols".formatted(MAX_COURSE_DESCRIPTION_SIZE));
        }

        return new CourseDescription(description);
    }
}
