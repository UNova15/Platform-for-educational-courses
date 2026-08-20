package refactor.course.domain.lesson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;
import refactor.common.exception.domain.DomainModificationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Lesson {
    private final Long id;
    private final Long moduleId;
    private int orderIndex;
    private boolean mandatory;
    private LessonTitle title;
    private Content content;

    public static Lesson createNew(
            long moduleId, LessonTitle title, Content content, int orderIndex, boolean mandatory) {
        if (moduleId < 0 || title == null || content == null || orderIndex < 0) {
            throw new DomainValidationException("Incorrect data to create lesson");
        }
        return new Lesson(null, moduleId, orderIndex, mandatory, title, content);
    }

    public void update(LessonTitle title, Content content, int orderIndex, boolean mandatory) {
        if (title == null || content == null || orderIndex < 0) {
            throw new DomainModificationException("Incorrect data to update lesson");
        }
        this.content = content;
        this.mandatory = mandatory;
        this.orderIndex = orderIndex;
        this.title = title;
    }

    public static Lesson restore(
            Long id, Long moduleId, LessonTitle title, Content content, int orderIndex, boolean mandatory) {
        return new Lesson(id, moduleId, orderIndex, mandatory, title, content);
    }
}
