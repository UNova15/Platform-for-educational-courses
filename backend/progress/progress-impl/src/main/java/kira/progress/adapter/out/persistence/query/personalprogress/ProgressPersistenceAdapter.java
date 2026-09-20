package kira.progress.adapter.out.persistence.query.personalprogress;

import common.domain.Id;
import kira.progress.application.port.in.query.personalprogress.LessonProgressSummary;
import kira.progress.application.port.in.query.personalprogress.TestProgressSummary;
import kira.progress.application.port.in.query.shared.TestAnswersView;
import kira.progress.application.port.out.persistance.query.PersonalProgressQueryPort;
import kira.progress.application.port.out.persistance.query.TestAnswersViewQueryPort;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class ProgressPersistenceAdapter implements PersonalProgressQueryPort, TestAnswersViewQueryPort {
    private final JdbcClient jdbcClient;

    @Override
    public List<LessonProgressSummary> findCompletedLessonsIn(Set<Id<Lesson>> lessonsIds, Id<User> userId) {
        return jdbcClient
                .sql("""
                    SELECT l.lesson_id,l.completed_at FROM progress.lessons_progress l
                    WHERE l.user_id = :userId AND l.lesson_id IN :lessonsIds
                    """)
                .param("userId", userId.value())
                .param("lessonsIds", lessonsIds)
                .query(LessonProgressSummary.class)
                .list();
    }

    @Override
    public List<TestProgressSummary> findCompletedTestAttemptsIn(Set<Id<Test>> testsIds, Id<User> userId) {
        return jdbcClient
                .sql("""
                    SELECT t.test_id,t.started_at,t.completed_at,t.score
                    FROM progress.test_submissions t
                    WHERE t.user_id = :userId AND t.test_id IN :testsIds
                    """)
                .param("userId", userId.value())
                .param("testsIds", testsIds)
                .query(TestProgressSummary.class)
                .list();
    }

    @Override
    public Optional<TestAnswersView> findTestAnswersViewByTestIdAndStudentId(Id<User> userId, Id<Test> testId) {
        Optional<Long> attemptId = jdbcClient
                .sql("SELECT t.id FROM progress.test_submissions t WHERE t.user_id = :userId AND t.test_id = :testId")
                .param("userId", userId.value())
                .param("testId", testId.value())
                .query(Long.class)
                .optional();

        if (attemptId.isEmpty()) return Optional.empty();

        return jdbcClient.sql("""
            SELECT t.question_id, t.option_id
            FROM progress.test_answers t
            WHERE t.test_submission_id = :attemptId
            """).param("attemptId", attemptId.get()).query(rs -> {
            Map<Long, List<Long>> optionsIdsByQuestionId = new HashMap<>();

            if (!rs.next()) return Optional.empty();

            do {
                long questionId = rs.getLong("question_id");
                long optionId = rs.getLong("option_id");

                optionsIdsByQuestionId
                        .computeIfAbsent(questionId, id -> new ArrayList<>())
                        .add(optionId);
            } while (rs.next());

            List<TestAnswersView.QuestionAnswer> answers = optionsIdsByQuestionId.entrySet().stream()
                    .map(option -> new TestAnswersView.QuestionAnswer(option.getKey(), option.getValue()))
                    .toList();

            return Optional.of(new TestAnswersView(testId.value(), answers));
        });
    }
}
