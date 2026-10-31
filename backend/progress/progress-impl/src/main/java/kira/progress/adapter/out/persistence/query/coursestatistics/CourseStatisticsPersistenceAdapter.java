package kira.progress.adapter.out.persistence.query.coursestatistics;

import common.domain.entity.Id;
import java.util.List;
import kira.progress.application.port.in.query.analytics.CompletedTestStudentsResult;
import kira.progress.application.port.in.query.analytics.EnrolledStudentsResult;
import kira.progress.application.port.in.query.analytics.StudentsLessonProgressResult;
import kira.progress.application.port.out.persistance.query.CourseStatisticsQueryPort;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class CourseStatisticsPersistenceAdapter implements CourseStatisticsQueryPort {
    private final JdbcClient dbClient;

    @Override
    public List<EnrolledStudentsResult> findEnrolledUsersByCourseId(Id<Course> courseId) {
        return dbClient.sql("SELECT e.user_id, e.created_at FROM progress.enrollments e WHERE e.course_id = :courseId")
                .param("courseId", courseId.value())
                .query(EnrolledStudentsResult.class)
                .list();
    }

    @Override
    public List<StudentsLessonProgressResult> findWatchedLessonStudentsByLessonId(Id<Lesson> lessonId) {
        return dbClient.sql(
                        "SELECT l.user_id,l.completed_at FROM progress.lessons_progress l WHERE l.lesson_id = :lessonId")
                .param("lessonId", lessonId.value())
                .query(StudentsLessonProgressResult.class)
                .list();
    }

    @Override
    public List<CompletedTestStudentsResult> findCompletedTestStudentsByTestId(Id<Test> testId) {
        return dbClient.sql("""
                    SELECT t.id,t.user_id,t.started_at,t.completed_at,t.score
                    FROM progress.test_submissions t
                    WHERE t.test_id = :testId
                    """)
                .param("testId", testId.value())
                .query(CompletedTestStudentsResult.class)
                .list();
    }
}
