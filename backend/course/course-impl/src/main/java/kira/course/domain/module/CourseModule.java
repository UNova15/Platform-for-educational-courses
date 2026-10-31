package kira.course.domain.module;

import common.domain.entity.Id;
import common.domain.exception.DomainStateException;
import common.domain.exception.DomainValidationException;
import kira.course.domain.CourseAggregateType;
import kira.course.domain.course.Course;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
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
    private CourseContentStatus status;

    public static CourseModule create(Id<Course> courseId, Title title, Description description, int orderIndex) {
        if (courseId == null || title == null || orderIndex < 0) {
            throw new DomainValidationException("Empty or negative data to create module", CourseAggregateType.MODULE);
        }
        return new CourseModule(null, courseId, orderIndex, title, description, CourseContentStatus.DRAFT);
    }

    public static CourseModule restore(
            Id<CourseModule> id,
            Id<Course> courseId,
            Title title,
            Description description,
            int orderIndex,
            CourseContentStatus status) {
        return new CourseModule(id, courseId, orderIndex, title, description, status);
    }

    public void updateInfo(Title title, Description description, int orderIndex) {
        if (title == null || orderIndex < 0) {
            throw new DomainValidationException("Empty or negative data to update module", CourseAggregateType.MODULE);
        }
        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
    }

    private boolean canPublish() {
        return status.canTransitionTo(CourseContentStatus.PUBLISHED);
    }

    public void publish() {
        if (!canPublish()) {
            throw new DomainStateException("Cannot publish module", CourseAggregateType.MODULE, id)
                    .withContext("status", status);
        }
        status = CourseContentStatus.PUBLISHED;
    }
}
