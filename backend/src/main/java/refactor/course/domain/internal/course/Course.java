package refactor.course.domain.internal.course;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.access.CourseAccessException;
import refactor.common.exception.domain.DomainValidationException;
import refactor.common.exception.domain.DomainModificationException;
import refactor.common.domain.Id;
import refactor.course.domain.internal.common.Description;
import refactor.course.domain.internal.common.Title;
import refactor.course.domain.external.User;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Course {
    public static final int MAX_MODULES_COUNT = 100;

    private final Id<Course> id;
    private Id<User> teacherId;
    private Tag tag;
    private LocalDateTime createdAt;
    private Title title;
    private Description description;

    public static Course createNew(Id<User> teacherId, Title title, Description description, Tag tag) {
        if (teacherId == null || title == null) {
            throw new DomainValidationException("Course title can not be empty");
        }

        return new Course(null, teacherId, tag, LocalDateTime.now(), title, description);
    }

    public static Course restore(
            Id<Course> id,
            Id<User> teacherId,
            Title title,
            Description description,
            Tag tag,
            LocalDateTime createdAt) {
        return new Course(id, teacherId, tag, createdAt, title, description);
    }

    public void updateCourseInfo(Title title, Description description, Tag tag) {
        if (title == null) {
            throw new DomainModificationException("Course title cannot be empty");
        }

        this.title = title;
        this.description = description;
        this.tag = tag;
    }

    public void verifyOwnership(Id<User> requesterId) {
        if (!requesterId.equals(teacherId)) {
            throw new CourseAccessException(id, requesterId);
        }
    }
}
