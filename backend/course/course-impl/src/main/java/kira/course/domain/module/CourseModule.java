package kira.course.domain.module;

import common.domain.Id;
import common.exception.DomainModificationException;
import common.exception.DomainValidationException;
import kira.course.domain.common.Description;
import kira.course.domain.common.Title;
import kira.course.domain.course.Course;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseModule {
    public static final int MAX_TESTS_COUNT = 100;
    public static final int MAX_LESSONS_COUNT = 100;

    private final Id<CourseModule> id;
    private final Id<Course> courseId;
    private int orderIndex;
    private Title title;
    private Description description;

    public static CourseModule createNew(Id<Course> courseId, Title title, Description description, int orderIndex) {
        if (courseId == null || title == null || orderIndex < 0) {
            throw new DomainValidationException("Incorrect data to create module");
        }
        return new CourseModule(null, courseId, orderIndex, title, description);
    }

    public static CourseModule restore(
            Id<CourseModule> id, Id<Course> courseId, Title title, Description description, int orderIndex) {
        return new CourseModule(id, courseId, orderIndex, title, description);
    }

    public void updateInfo(Title title, Description description, int orderIndex) {
        if (title == null || orderIndex < 0) {
            throw new DomainModificationException("Incorrect data to update module");
        }
        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
    }
}
