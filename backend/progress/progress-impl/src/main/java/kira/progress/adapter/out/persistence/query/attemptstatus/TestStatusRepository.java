package kira.progress.adapter.out.persistence.query.attemptstatus;

import kira.progress.adapter.out.persistence.testattempt.TestAttemptEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface TestStatusRepository extends CrudRepository<TestAttemptEntity,Long> {
    @Query("""
        SELECT 1 FROM progress.test_submissions s
        WHERE s.user_id = :studentId AND s.test_id = :testId
        AND s.started_at IS NOT NULL AND s.completed_at IS NOT NULL
        """)
    boolean isTestCompleted(long studentId, long testId);

    @Query("""
            SELECT 1 FROM progress.test_submissions s
            WHERE s.user_id = :studentId AND s.test_id = :testId
            AND s.started_at IS NOT NULL AND s.completed_at IS NULL
            """)
    boolean isStudentSolvingTest(long studentId, long testId);
}
