package org.platform.platformforeducationalcourses.domain.course;

import java.time.LocalDateTime;
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

    public static Course createNew(long teacherId, String title, String description, Tag tag) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Course title can not be empty");
        }

        return new Course(null, teacherId, title, description, tag, LocalDateTime.now());
    }

    public void updateCourse(String title, String description, Tag tag) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Course title can not be empty");
        }

        this.title = title;
        this.description = description;
        this.tag = tag;
    }
}
