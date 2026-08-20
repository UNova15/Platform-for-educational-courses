package refactor.course.domain.course;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseTitle {
    public static final int MAX_COURSE_TITLE_SIZE = 100;

    private final String value;

    public static CourseTitle of(String title) {
        if (title == null || title.isBlank() || title.length() > MAX_COURSE_TITLE_SIZE) {
            throw new DomainValidationException("Incorrect data to create course title");
        }
        return new CourseTitle(title);
    }
}
