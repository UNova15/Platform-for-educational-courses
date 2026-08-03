package org.platform.platformforeducationalcourses.domain.course;

import java.util.Collections;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseModule {
    private final Long id;

    private final Long courseId;
    private String title;
    private String description;
    private int orderIndex;

    @Getter(AccessLevel.NONE)
    private Set<Long> lessonsIds;

    @Getter(AccessLevel.NONE)
    private Set<Long> autoTestsIds;

    public Set<Long> getLessonsIds() {
        return Collections.unmodifiableSet(lessonsIds);
    }

    public Set<Long> getAutoTestsIds() {
        return Collections.unmodifiableSet(autoTestsIds);
    }

    public static CourseModule createNew(long courseId, String title, String description, int orderIndex) {
        if (courseId < 0 || title == null || title.isBlank() || orderIndex < 0) {
            throw new IllegalArgumentException("Incorrect data to create module");
        }
        return new CourseModule(null, courseId, title, description, orderIndex, null, null);
    }

    public static CourseModule restore(
            Long id,
            Long courseId,
            String title,
            String description,
            int orderIndex,
            Set<Long> lessonsIds,
            Set<Long> autoTestsIds) {
        return new CourseModule(id, courseId, title, description, orderIndex, lessonsIds, autoTestsIds);
    }

    public void updateInfo(String title, String description, int orderIndex) {
        if (title == null || title.isBlank() || orderIndex < 0) {
            throw new IllegalArgumentException("Incorrect data to update module");
        }
        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
    }
}
