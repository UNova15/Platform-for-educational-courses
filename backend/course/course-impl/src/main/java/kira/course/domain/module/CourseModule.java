package kira.course.domain.module;

import common.domain.Id;
import common.exception.core.DomainStateException;
import common.exception.core.DomainValidationException;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Status;
import kira.course.domain.shared.Title;
import kira.course.domain.course.Course;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseModule {
    private final Id<CourseModule> id;
    private final Id<Course> courseId;
    private int orderIndex;
    private Title title;
    private Description description;
    private Status status;

    public static CourseModule create(Id<Course> courseId, Title title, Description description, int orderIndex) {
        if (courseId == null || title == null || orderIndex < 0) {
            throw new DomainValidationException("Empty or negative data to create module");
        }
        return new CourseModule(null, courseId, orderIndex, title, description, Status.DRAFT);
    }

    public static CourseModule restore(
            Id<CourseModule> id,
            Id<Course> courseId,
            Title title,
            Description description,
            int orderIndex,
            Status status) {
        return new CourseModule(id, courseId, orderIndex, title, description, status);
    }

    public void updateInfo(Title title, Description description, int orderIndex) {
        if (title == null || orderIndex < 0) {
            throw new DomainValidationException("Empty or negative data to update module");
        }
        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
    }

    public boolean canPublish() {
        return status.canTransitionTo(Status.PUBLISHED);
    }

    public void publish() {
        if (!canPublish()) {
            throw new DomainStateException("Cannot publish module", id).withContext("status", status);
        }
        status = Status.PUBLISHED;
    }
}
