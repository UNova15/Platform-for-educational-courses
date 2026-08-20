package refactor.course.domain.lesson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonTitle {
    public static final int MAX_LESSON_TITLE_SIZE = 100;

    private final String value;

    public static LessonTitle of(String title) {
        if (title == null || title.isBlank() || title.length() > MAX_LESSON_TITLE_SIZE) {
            throw new DomainValidationException("Incorrect data to create lesson title");
        }
        return new LessonTitle(title);
    }
}
