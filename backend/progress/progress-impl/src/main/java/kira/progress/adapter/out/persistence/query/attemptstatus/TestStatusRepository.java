package kira.progress.adapter.out.persistence.query.attemptstatus;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class TestStatusRepository {
    private final JdbcClient dbClient;

    boolean isTestCompleted(long studentId, long testId) {
        return dbClient.sql("""
                        SELECT EXISTS
                            (SELECT 1 FROM progress.test_submissions s
                        WHERE s.user_id = :studentId AND s.test_id = :testId
                        AND s.started_at IS NOT NULL AND s.completed_at IS NOT NULL
                        )
                        """)
                .param("studentId", studentId)
                .param("testId", testId)
                .query(Boolean.class)
                .single();
    }

    boolean isStudentSolvingTest(long studentId, long testId) {
        return dbClient.sql("""
                        SELECT EXISTS
                            (SELECT 1 FROM progress.test_submissions s
                        WHERE s.user_id = :studentId AND s.test_id = :testId
                        AND s.started_at IS NOT NULL AND s.completed_at IS NULL
                        )
                        """)
                .param("studentId", studentId)
                .param("testId", testId)
                .query(Boolean.class)
                .single();
    }
}
