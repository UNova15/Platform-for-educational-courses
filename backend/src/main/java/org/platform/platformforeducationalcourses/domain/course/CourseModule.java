package org.platform.platformforeducationalcourses.domain.course;

import java.util.Collections;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.platform.platformforeducationalcourses.domain.course.reference.LessonRef;
import org.platform.platformforeducationalcourses.domain.course.reference.TestRef;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "modules")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseModule {
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

    public static CourseModule createNew(long courseId, String title, String description, int orderIndex) {
        if (courseId < 0 || title == null || title.isBlank() || orderIndex < 0) {
            throw new IllegalArgumentException("Incorrect data to create module");
        }
        return new CourseModule(null, courseId, title, description, orderIndex, null, null);
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
