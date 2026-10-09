package kira.course.domain.course;

import common.domain.Id;
import common.exception.core.DomainStateException;
import common.exception.core.DomainValidationException;
import java.time.LocalDateTime;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import kira.course.domain.markers.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Course {
    private final Id<Course> id;
    private Id<User> teacherId;
    private Tag tag;
    private LocalDateTime createdAt;
    private Title title;
    private Description description;
    private CourseStatus status;

    public static Course create(Id<User> teacherId, Title title, Description description, Tag tag) {
        if (teacherId == null || title == null) {
            throw new DomainValidationException("Empty data to create course");
        }

        return new Course(null, teacherId, tag, LocalDateTime.now(), title, description, CourseStatus.DRAFT);
    }

    public static Course restore(
            Id<Course> id,
            Id<User> teacherId,
            Title title,
            Description description,
            Tag tag,
            LocalDateTime createdAt,
            CourseStatus status) {
        return new Course(id, teacherId, tag, createdAt, title, description, status);
    }

    public boolean isArchived() {
        return status == CourseStatus.ARCHIVED;
    }

    public void updateCourseInfo(Title title, Description description, Tag tag) {
        if (title == null) {
            throw new DomainValidationException("Course title cannot be empty");
        }

        if (isArchived()) {
            throw new DomainStateException("Cannot update course info", id).withContext("status", status);
        }

        this.title = title;
        this.description = description;
        this.tag = tag;
    }

    public boolean isDraft() {
        return status == CourseStatus.DRAFT;
    }

    public boolean canPublish() {
        return status.canTransitionTo(CourseStatus.PUBLISHED);
    }

    public void publish() {
        if (!canPublish()) {
            throw new DomainStateException("It is not possible to publish the course from this state", id)
                    .withContext("status", status);
        }
        status = CourseStatus.PUBLISHED;
    }

    public boolean canArchive() {
        return status.canTransitionTo(CourseStatus.ARCHIVED);
    }

    public void archive() {
        if (!canArchive()) {
            throw new DomainStateException(
                            "It is not possible to move the course to the archive from the current state", id)
                    .withContext("status", status);
        }
        status = CourseStatus.ARCHIVED;
    }

    public boolean isOwnedBy(Id<User> requesterId) {
        return teacherId.equals(requesterId);
    }
}
