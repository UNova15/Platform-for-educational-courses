package kira.course.adapter.out.persistence.query.coursedetail;

import common.domain.entity.Id;
import kira.course.application.port.out.persistance.query.CourseDetailsProviderPort;
import kira.course.domain.course.Course;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.course.valueobject.CourseDetails;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseDetailProviderAdapter implements CourseDetailsProviderPort {
    private final JdbcClient dbClient;
    private final ResultMapper mapper;

    @Override
    public CourseDetails findCourseDetailsByModuleId(Id<CourseModule> moduleId) {
        return dbClient.sql("""
            SELECT c.status,c.id FROM course.courses c
            WHERE id = (
                SELECT course_id FROM course.modules
                WHERE id = :moduleId
            )
            """)
                .param("moduleId", moduleId.value())
                .query(mapper)
                .single();
    }

    @Override
    public CourseDetails findCourseDetailsByLessonId(Id<Lesson> lessonId) {
        return dbClient.sql("""
            SELECT c.status,c.id FROM course.courses c
            WHERE id = (
                SELECT m.course_id FROM course.modules m
                JOIN course.lessons l ON l.module_id = m.id
                WHERE l.id = :lessonId
            )
            """)
                .param("lessonId", lessonId.value())
                .query(mapper)
                .single();
    }

    @Override
    public CourseDetails findCourseDetailsByTestId(Id<Test> testId) {
        return dbClient.sql("""
            SELECT c.status,c.id FROM course.courses c
            WHERE id = (
                SELECT m.course_id FROM course.modules m
                JOIN course.tests t ON t.module_id = m.id
                WHERE t.id = :testId
            )
            """).param("testId", testId.value()).query(mapper).single();
    }

    @Override
    public Optional<CourseStatus> findCourseDetailsById(Id<Course> courseId) {
        return dbClient.sql("SELECT status FROM course.courses WHERE id = :id")
                .param("id", courseId.value())
                .query(CourseStatus.class)
                .optional();
    }
}
