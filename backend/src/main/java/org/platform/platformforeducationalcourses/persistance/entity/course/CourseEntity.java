package org.platform.platformforeducationalcourses.persistance.entity.course;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.platform.platformforeducationalcourses.domain.course.Tag;
import org.platform.platformforeducationalcourses.persistance.entity.reference.ModuleRef;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "courses")
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

    @MappedCollection(idColumn = "course_id")
    private Set<ModuleRef> modules;

    public Set<ModuleRef> getModules() {
        return Collections.unmodifiableSet(modules);
    }
}
