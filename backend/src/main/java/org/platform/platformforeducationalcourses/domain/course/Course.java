package org.platform.platformforeducationalcourses.domain.course;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Course {
    private final Long id;

    private Long teacherId;
    private String title;
    private String description;
    private Tag tag;
    private LocalDateTime createdAt;

    @Getter(AccessLevel.NONE)
    private Set<Long> modulesIds;

    public Set<Long> getModulesIds() {
        return Collections.unmodifiableSet(modulesIds);
    }

    public static Course createNew(long teacherId, String title, String description, Tag tag) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Course title can not be empty");
        }

        return new Course(null, teacherId, title, description, tag, LocalDateTime.now(), null);
    }

    public static Course restore(
            Long id,
            Long teacherId,
            String title,
            String description,
            Tag tag,
            LocalDateTime createdAt,
            Set<Long> modulesIds) {
        return new Course(id, teacherId, title, description, tag, createdAt, modulesIds);
    }

    public void updateCourseInfo(String title, String description, Tag tag) {
        if (title == null || title.isBlank() || modulesIds == null) {
            throw new IllegalArgumentException("Course data can not be empty");
        }

        this.title = title;
        this.description = description;
        this.tag = tag;
    }
}
