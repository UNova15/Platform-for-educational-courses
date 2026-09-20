package kira.course.adapter.out.persistence.query.structure;

import common.domain.Id;
import kira.course.api.CourseModuleStructure;
import kira.course.application.port.out.persistance.query.CourseStructureQueryPort;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CourseStructureQueryAdapter implements CourseStructureQueryPort {
    private final JdbcClient jdbcClient;

    @Override
    public Optional<Id<Course>> findCourseIdByTestId(Id<Test> testId) {
        return jdbcClient
                .sql("""
                        SELECT c.id FROM course.tests t
                        JOIN course.modules m ON t.module_id = m.id
                        JOIN course.courses c ON c.id = m.course_id
                        WHERE t.id = :testId
                    """)
                .param("testId", testId.value())
                .query(Long.class)
                .optional()
                .map(Id::of);
    }

    @Override
    public Optional<Id<Course>> findCourseIdByLessonId(Id<Lesson> lessonId) {
        return jdbcClient
                .sql("""
                    SELECT c.id FROM course.lessons l
                    JOIN course.modules m ON l.module_id = m.id
                    JOIN course.courses c ON c.id = m.course_id
                    WHERE l.id = :lessonId
                    """)
                .param("lessonId", lessonId.value())
                .query(Long.class)
                .optional()
                .map(Id::of);
    }

    @Override
    public Optional<CourseModuleStructure> findModuleStructureById(Id<CourseModule> moduleId) {
        Optional<Long> courseId = jdbcClient
                .sql("SELECT m.course_id FROM course.modules m WHERE m.id = :moduleId")
                .param("moduleId", moduleId.value())
                .query(Long.class)
                .optional();

        if (courseId.isEmpty()) {
            return Optional.empty();
        }

        Set<Long> lessonIds = jdbcClient
                .sql("SELECT l.id FROM course.lessons l WHERE l.module_id = :moduleId")
                .param("moduleId", moduleId.value())
                .query(Long.class)
                .set();

        Set<Long> testIds = jdbcClient
                .sql("SELECT t.id FROM course.tests t WHERE t.module_id = :moduleId")
                .param("moduleId", moduleId.value())
                .query(Long.class)
                .set();

        return Optional.of(new CourseModuleStructure(courseId.get(), lessonIds, testIds));
    }
}
