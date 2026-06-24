package org.platform.platformforeducationalcourses.domain.course;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.platform.platformforeducationalcourses.domain.course.reference.ModuleRef;
import org.platform.platformforeducationalcourses.dto.course.CourseUpdateDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "courses")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Course {
    @Id
    private final Long id;

    private Long teacherId;
    private String title;
    private String description;
    private Tag tag;
    private LocalDateTime createdAt;

    @MappedCollection(idColumn = "course_id")
    private Set<ModuleRef> modules;

    public Set<ModuleRef> getModules() {
        return Collections.unmodifiableSet(modules);
    }

    public static Course createNew(long teacherId, String title, String description, Tag tag) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Course title can not be empty");
        }

        return new Course(null, teacherId, title, description, tag, LocalDateTime.now(), null);
    }

    public void updateCourse(CourseUpdateDto updateDto) {
        if (updateDto.title() == null || updateDto.title().isBlank()) {
            throw new IllegalArgumentException("Course title can not be empty");
        }

        this.title = updateDto.title();
        this.description = updateDto.description();
        this.tag = updateDto.tag();
    }
}
