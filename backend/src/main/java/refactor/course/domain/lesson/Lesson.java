package refactor.course.domain.lesson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.domain.Id;
import refactor.common.exception.DomainValidationException;
import refactor.common.exception.DomainModificationException;
import refactor.course.domain.common.Title;
import refactor.course.domain.module.CourseModule;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Lesson {
    private final Id<Lesson> id;
    private final Id<CourseModule> moduleId;
    private int orderIndex;
    private boolean mandatory;
    private Title title;
    private Content content;

    public static Lesson createNew(
            Id<CourseModule> moduleId, Title title, Content content, int orderIndex, boolean mandatory) {
        if (moduleId == null || title == null || content == null || orderIndex < 0) {
            throw new DomainValidationException("Incorrect data to create lesson");
        }
        return new Lesson(null, moduleId, orderIndex, mandatory, title, content);
    }

    public void update(Title title, Content content, int orderIndex, boolean mandatory) {
        if (title == null || content == null || orderIndex < 0) {
            throw new DomainModificationException("Incorrect data to update lesson");
        }
        this.content = content;
        this.mandatory = mandatory;
        this.orderIndex = orderIndex;
        this.title = title;
    }

    public static Lesson restore(
            Id<Lesson> id, Id<CourseModule> moduleId, Title title, Content content, int orderIndex, boolean mandatory) {
        return new Lesson(id, moduleId, orderIndex, mandatory, title, content);
    }
}
