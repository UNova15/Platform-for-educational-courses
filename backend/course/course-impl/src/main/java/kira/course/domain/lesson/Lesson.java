package kira.course.domain.lesson;

import common.domain.Id;
import common.exception.core.DomainStateException;
import common.exception.core.DomainValidationException;
import kira.course.domain.shared.Status;
import kira.course.domain.shared.Title;
import kira.course.domain.module.CourseModule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Lesson {
    private final Id<Lesson> id;
    private final Id<CourseModule> moduleId;
    private int orderIndex;
    private boolean mandatory;
    private Title title;
    private LessonContent content;
    private Status status;

    public static Lesson create(
            Id<CourseModule> moduleId, Title title, LessonContent content, int orderIndex, boolean mandatory) {
        if (moduleId == null || title == null || content == null || orderIndex < 0) {
            throw new DomainValidationException("Empty or negative data to create lesson");
        }
        return new Lesson(null, moduleId, orderIndex, mandatory, title, content, Status.DRAFT);
    }

    public void update(Title title, LessonContent content, int orderIndex, boolean mandatory) {
        if (title == null || content == null || orderIndex < 0) {
            throw new DomainValidationException("Empty or negative data to update lesson");
        }
        this.content = content;
        this.mandatory = mandatory;
        this.orderIndex = orderIndex;
        this.title = title;
    }

    public static Lesson restore(
            Id<Lesson> id,
            Id<CourseModule> moduleId,
            Title title,
            LessonContent content,
            int orderIndex,
            boolean mandatory,
            Status status) {
        return new Lesson(id, moduleId, orderIndex, mandatory, title, content, status);
    }

    public boolean canPublish() {
        return status.canTransitionTo(Status.PUBLISHED);
    }

    public boolean isDraft() {
        return status == Status.DRAFT;
    }

    public void publish() {
        if (!canPublish()) {
            throw new DomainStateException("Cannot publish lesson", id).withContext("status", status);
        }
        status = Status.PUBLISHED;
    }
}
