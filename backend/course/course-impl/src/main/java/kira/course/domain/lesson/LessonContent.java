package kira.course.domain.lesson;

import common.domain.exception.LengthViolationException;
import common.domain.exception.ValueObjectValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonContent {
    public static final int MAX_CONTENT_LENGTH = 10_000;
    public static final int MIN_CONTENT_LENGTH = 1;

    private final LessonContentType type;
    private final String value;

    public static LessonContent of(LessonContentType type, String content) {
        if (content == null || type == null) {
            throw new ValueObjectValidationException("Empty data to create lessons content");
        }

        if (content.length() <= MIN_CONTENT_LENGTH || content.length() > MAX_CONTENT_LENGTH) {
            throw new LengthViolationException(
                    "Incorrect lesson content length", content.length(), MAX_CONTENT_LENGTH, MIN_CONTENT_LENGTH);
        }

        return new LessonContent(type, content);
    }
}
