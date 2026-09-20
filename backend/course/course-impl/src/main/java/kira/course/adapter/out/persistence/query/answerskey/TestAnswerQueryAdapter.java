package kira.course.adapter.out.persistence.query.answerskey;

import common.domain.Id;
import kira.course.api.CourseAnswerKey;
import kira.course.application.port.out.persistance.query.TestAnswerKeyQueryPort;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class TestAnswerQueryAdapter implements TestAnswerKeyQueryPort {
    private final JdbcClient jdbcClient;

    @Override
    public Optional<CourseAnswerKey> findAnswerKeyByTestId(Id<Test> testId) {
        return jdbcClient.sql("""
            SELECT q.id AS question_id, o.id AS option_id
            FROM course.test_questions q 
            JOIN course.question_options o ON o.question_id = q.id
            WHERE t.test_id = :testId
            """).param("testId", testId.value()).query(rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }

            Map<Long, Set<Long>> keys = new HashMap<>();

            do {
                long questionId = rs.getLong("question_id");
                long optionId = rs.getLong("option_id");

                keys.computeIfAbsent(questionId, value -> new HashSet<>()).add(optionId);
            } while (rs.next());

            return Optional.of(new CourseAnswerKey(testId.value(), keys));
        });
    }
}
