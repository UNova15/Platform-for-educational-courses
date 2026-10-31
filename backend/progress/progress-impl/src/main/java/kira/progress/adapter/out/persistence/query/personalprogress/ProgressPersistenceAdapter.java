package kira.progress.adapter.out.persistence.query.personalprogress;

import common.domain.entity.Id;
import java.util.*;
import kira.progress.application.port.in.query.personalprogress.LessonProgressSummary;
import kira.progress.application.port.in.query.personalprogress.TestProgressSummary;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.application.port.out.persistance.query.PersonalProgressQueryPort;
import kira.progress.application.port.out.persistance.query.TestAnswersViewQueryPort;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ProgressPersistenceAdapter implements PersonalProgressQueryPort, TestAnswersViewQueryPort {
    private final JdbcClient dbClient;

    @Override
    public List<LessonProgressSummary> findCompletedLessonsIn(Set<Id<Lesson>> lessonsIds, Id<User> userId) {
        List<Long> ids = lessonsIds.stream().map(Id::value).toList();

        return dbClient.sql("""
            SELECT l.lesson_id,l.completed_at FROM progress.lessons_progress l
            WHERE l.user_id = :userId AND l.lesson_id IN (:lessonsIds)
            """)
                .param("userId", userId.value())
                .param("lessonsIds", ids)
                .query(LessonProgressSummary.class)
                .list();
    }

    @Override
    public List<TestProgressSummary> findCompletedTestAttemptsIn(Set<Id<Test>> testsIds, Id<User> userId) {
        List<Long> ids = testsIds.stream().map(Id::value).toList();

        return dbClient.sql("""
            SELECT t.test_id,t.started_at,t.completed_at,t.score
            FROM progress.test_submissions t
            WHERE t.user_id = :userId AND t.test_id IN (:testsIds)
            """)
                .param("userId", userId.value())
                .param("testsIds", ids)
                .query(TestProgressSummary.class)
                .list();
    }

    @Override
    public Optional<TestAnswersView> findTestAnswersViewByTestIdAndStudentId(Id<User> userId, Id<Test> testId) {
        return dbClient.sql("""
            SELECT a.question_id, a.option_id
            FROM progress.test_submissions t
            JOIN progress.test_answers a ON t.id = a.test_submission_id
            WHERE t.user_id = :userId AND t.test_id = :testId
            """)
                .param("userId", userId.value())
                .param("testId", testId.value())
                .query(PersonalProgressMapper.extractAnswers(testId));
    }

    @Override
    public List<Id<Course>> findCoursesIdsThatUsersIsEnrolledIn(Id<User> userId) {
        List<Long> ids = dbClient.sql("SELECT e.course_id FROM progress.enrollments e WHERE e.user_id = :userId")
                .param("userId", userId.value())
                .query(Long.class)
                .list();

        return ids.stream().map(Id::<Course>of).toList();
    }
}
