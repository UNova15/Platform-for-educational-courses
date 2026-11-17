package kira.course.adapter.out.persistence.query.internal;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.api.CourseProviderQuery;
import kira.course.application.port.out.persistance.query.CourseProviderPort;
import kira.course.domain.course.Course;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourseInternalPersistenceAdapter implements CourseProviderPort {
    private final JdbcClient dbClient;

    @Override
    public Optional<CourseProviderQuery.CourseInfo> getCourseInfo(Id<Course> courseId) {
        return dbClient.sql("SELECT title,teacher_id FROM course.courses WHERE id = :courseId")
                .param("courseId", courseId.value())
                .query(CourseProviderQuery.CourseInfo.class)
                .optional();
    }

    @Override
    public Optional<CourseProviderQuery.TestInfo> getTestInfo(Id<Test> testId) {
        return dbClient.sql("""
                        SELECT
                            c.title AS course_title,
                            t.title AS test_title,
                            c.teacher_id AS teacher_id
                        FROM course.tests t
                        JOIN course.modules m ON t.module_id = m.id
                        JOIN course.courses c ON c.id = m.course_id
                        WHERE t.id = :testId
                        """)
                .param("testId", testId.value())
                .query(CourseProviderQuery.TestInfo.class)
                .optional();
    }
}
