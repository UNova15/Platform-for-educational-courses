package kira.progress.adapter.out.persistence.query.coursestatistics;

import common.domain.Id;
import kira.progress.application.port.in.query.analytics.CompletedTestStudentsResult;
import kira.progress.application.port.in.query.analytics.EnrolledStudentsResult;
import kira.progress.application.port.in.query.analytics.StudentsLessonProgressResult;
import kira.progress.application.port.out.persistance.query.CourseStatisticsQueryPort;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseStatisticsPersistenceAdapter implements CourseStatisticsQueryPort {
    private final JdbcClient jdbcClient;

    @Override
    public List<EnrolledStudentsResult> findEnrolledUsersByCourseId(Id<Course> courseId) {
        return jdbcClient
                .sql("SELECT e.user_id, e.created_at FROM progress.enrollments e WHERE e.course_id = :courseId")
                .param("courseId", courseId.value())
                .query(EnrolledStudentsResult.class)
                .list();
    }

    @Override
    public List<StudentsLessonProgressResult> findWatchedLessonStudentsByLessonId(Id<Lesson> lessonId) {
        return jdbcClient
                .sql("SELECT l.user_id,l.completed_at FROM progress.lessons_progress l WHERE l.lesson_id = :lessonId")
                .param("lessonId", lessonId.value())
                .query(StudentsLessonProgressResult.class)
                .list();
    }

    @Override
    public List<CompletedTestStudentsResult> findCompletedTestStudentsByTestId(Id<Test> testId) {
        return jdbcClient
                .sql("""
                    SELECT t.id,t.user_id,t.started_at,t.completed_at,t.score
                    FROM progress.test_submissions t
                    WHERE t.test_id = :testId
                    AND t.started_at IS NOT NULL
                    AND t.completed_at IS NOT NULL
                    """)
                .param("testId", testId.value())
                .query(CompletedTestStudentsResult.class)
                .list();
    }

    @Override
    public List<Id<Course>> findCoursesIdsThatUsersIsEnrolledIn(Id<User> userId) {
        List<Long> ids = jdbcClient
                .sql("SELECT e.course_id FROM progress.enrollments e WHERE e.user_id = :userId")
                .param("userId", userId.value())
                .query(Long.class)
                .list();

        return ids.stream().map(Id::<Course>of).toList();
    }
}
