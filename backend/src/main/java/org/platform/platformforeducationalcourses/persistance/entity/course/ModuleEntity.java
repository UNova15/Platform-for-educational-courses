package org.platform.platformforeducationalcourses.persistance.entity.course;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import refactor.course.domain.module.CourseModule;
import org.platform.platformforeducationalcourses.persistance.entity.reference.LessonRef;
import org.platform.platformforeducationalcourses.persistance.entity.reference.TestRef;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "modules")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModuleEntity {
    @Id
    private final Long id;

    private final Long courseId;
    private String title;
    private String description;
    private int orderIndex;

    @MappedCollection(idColumn = "module_id")
    Set<LessonRef> lessons;

    @MappedCollection(idColumn = "module_id")
    Set<TestRef> autoTests;

    public Set<LessonRef> getLessons() {
        return Collections.unmodifiableSet(lessons);
    }

    public Set<TestRef> getAutoTests() {
        return Collections.unmodifiableSet(autoTests);
    }

    public static ModuleEntity fromModule(CourseModule module) {
        Set<LessonRef> lessonRefs =
                module.lessonsIds().stream().map(LessonRef::new).collect(Collectors.toSet());
        Set<TestRef> autoTestsRefs =
                module.autoTestsIds().stream().map(TestRef::new).collect(Collectors.toSet());

        return new ModuleEntity(
                module.getId(),
                module.getCourseId(),
                module.getTitle(),
                module.getDescription(),
                module.getOrderIndex(),
                lessonRefs,
                autoTestsRefs);
    }
}
