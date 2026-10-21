package kira.course.domain.lesson;

import common.domain.entity.Id;
import common.domain.exception.DomainStateException;
import common.domain.exception.DomainValidationException;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.shared.Title;
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
    private CourseContentStatus status;

    public static Lesson create(
            Id<CourseModule> moduleId, Title title, LessonContent content, int orderIndex, boolean mandatory) {
        if (moduleId == null || title == null || content == null || orderIndex < 0) {
            throw new DomainValidationException("Empty or negative data to create lesson", CourseAggregateType.LESSON);
        }
        return new Lesson(null, moduleId, orderIndex, mandatory, title, content, CourseContentStatus.DRAFT);
    }

    public void update(Title title, LessonContent content, int orderIndex, boolean mandatory) {
        if (title == null || content == null || orderIndex < 0) {
            throw new DomainValidationException("Empty or negative data to update lesson", CourseAggregateType.LESSON);
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
            CourseContentStatus status) {
        return new Lesson(id, moduleId, orderIndex, mandatory, title, content, status);
    }

    private boolean canPublish() {
        return status.canTransitionTo(CourseContentStatus.PUBLISHED);
    }

    public boolean isDraft() {
        return status == CourseContentStatus.DRAFT;
    }

    public boolean isPublish() {
        return status == CourseContentStatus.PUBLISHED;
    }

    public void publish() {
        if (!canPublish()) {
            throw new DomainStateException("Cannot publish lesson", CourseAggregateType.LESSON, id)
                    .withContext("status", status);
        }
        status = CourseContentStatus.PUBLISHED;
    }
}
