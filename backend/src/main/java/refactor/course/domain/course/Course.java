package refactor.course.domain.course;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.access.CourseAccessException;
import refactor.common.exception.domain.DomainValidationException;
import refactor.common.exception.domain.DomainModificationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Course {
    public static final int MAX_MODULES_COUNT = 100;

    private final Long id;
    private Long teacherId;
    private Tag tag;
    private LocalDateTime createdAt;
    private CourseTitle title;
    private CourseDescription description;

    @Getter(AccessLevel.NONE)
    private Set<Long> modulesIds;

    public Set<Long> getModulesIds() {
        return Collections.unmodifiableSet(modulesIds);
    }

    public static Course createNew(long teacherId, CourseTitle title, CourseDescription description, Tag tag) {
        if (teacherId < 0 || title == null) {
            throw new DomainValidationException("Course title can not be empty");
        }

        return new Course(null, teacherId, tag, LocalDateTime.now(), title, description, new HashSet<>());
    }

    public static Course restore(
            Long id,
            Long teacherId,
            CourseTitle title,
            CourseDescription description,
            Tag tag,
            LocalDateTime createdAt,
            Set<Long> modulesIds) {
        return new Course(id, teacherId, tag, createdAt, title, description, modulesIds);
    }

    public void updateCourseInfo(CourseTitle title, CourseDescription description, Tag tag) {
        if (title == null) {
            throw new DomainModificationException("Course title cannot be empty");
        }

        this.title = title;
        this.description = description;
        this.tag = tag;
    }

    public void addModule(long moduleId) {
        if (moduleId < 0 || modulesIds.size() > MAX_MODULES_COUNT) {
            throw new DomainModificationException("Incorrect data to add module in course");
        }
        modulesIds.add(moduleId);
    }

    public void verifyOwnership(long requesterId) {
        if (requesterId != teacherId) {
            throw new CourseAccessException(id, requesterId);
        }
    }
}
