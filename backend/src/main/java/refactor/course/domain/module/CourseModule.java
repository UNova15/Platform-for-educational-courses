package refactor.course.domain.module;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;
import refactor.common.exception.domain.DomainModificationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseModule {
    public static final int MAX_TESTS_COUNT = 100;
    public static final int MAX_LESSONS_COUNT = 100;

    private final Long id;
    private final Long courseId;
    private int orderIndex;
    private ModuleTitle title;
    private ModuleDescription description;

    @Getter(AccessLevel.NONE)
    private Set<Long> lessonsIds;

    @Getter(AccessLevel.NONE)
    private Set<Long> autoTestsIds;

    public Set<Long> lessonsIds() {
        return Collections.unmodifiableSet(lessonsIds);
    }

    public Set<Long> autoTestsIds() {
        return Collections.unmodifiableSet(autoTestsIds);
    }

    public static CourseModule createNew(
            long courseId, ModuleTitle title, ModuleDescription description, int orderIndex) {
        if (courseId < 0 || title == null || orderIndex < 0) {
            throw new DomainValidationException("Incorrect data to create module");
        }
        return new CourseModule(null, courseId, orderIndex, title, description, new HashSet<>(), new HashSet<>());
    }

    public static CourseModule restore(
            Long id,
            Long courseId,
            ModuleTitle title,
            ModuleDescription description,
            int orderIndex,
            Set<Long> lessonsIds,
            Set<Long> autoTestsIds) {
        return new CourseModule(id, courseId, orderIndex, title, description, lessonsIds, autoTestsIds);
    }

    public void updateInfo(ModuleTitle title, ModuleDescription description, int orderIndex) {
        if (title == null || orderIndex < 0) {
            throw new DomainModificationException("Incorrect data to update module");
        }
        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
    }

    public void addTest(long testId) {
        if (testId < 0 || autoTestsIds.size() > MAX_TESTS_COUNT) {
            throw new DomainModificationException("Incorrect data to add test in module");
        }
        autoTestsIds.add(testId);
    }

    public void addLesson(long lessonId) {
        if (lessonId < 0 || lessonsIds.size() > MAX_LESSONS_COUNT) {
            throw new DomainModificationException("Incorrect data to add test in module");
        }
        autoTestsIds.add(lessonId);
    }
}
