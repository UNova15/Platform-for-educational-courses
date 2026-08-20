package org.platform.platformforeducationalcourses.persistance.entity.course;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import refactor.course.domain.course.Course;
import refactor.course.domain.course.Tag;
import org.platform.platformforeducationalcourses.persistance.entity.reference.ModuleRef;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "courses")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseEntity {
    @Id
    private final Long id;

    private Long teacherId;
    private String title;
    private String description;
    private Tag tag;
    private LocalDateTime createdAt;

    @Getter(AccessLevel.NONE)
    @MappedCollection(idColumn = "course_id")
    private Set<ModuleRef> modules;

    public Set<ModuleRef> getModules() {
        return Collections.unmodifiableSet(modules);
    }

    public static CourseEntity fromCourse(Course course) {
        Set<ModuleRef> refs =
                course.getModulesIds().stream().map(ModuleRef::new).collect(Collectors.toSet());

        return new CourseEntity(
                course.getId(),
                course.getTeacherId(),
                course.getTitle(),
                course.getDescription(),
                course.getTag(),
                course.getCreatedAt(),
                refs);
    }
}
