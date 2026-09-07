package refactor.course.domain.lesson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Content {
    public static final int MAX_CONTENT_LENGTH = 10_000;

    private final ContentType type;
    private final String value;

    public static Content of(ContentType type, String content) {
        if (content == null || content.isBlank() || type == null || content.length() > MAX_CONTENT_LENGTH) {
            throw new DomainValidationException("Incorrect data to create lessons content");
        }
        return new Content(type, content);
    }
}
