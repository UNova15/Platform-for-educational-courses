package kira.progress.adapter.out.persistence.testattempt;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

interface DataTestAttemptRepository extends CrudRepository<TestAttemptEntity, Long> {

    Optional<TestAttemptEntity> findByTestIdAndUserId(long testId, long userId);

    boolean existsByUserIdAndTestId(long userId, long testId);

    @Query("DELETE FROM progress.test_submissions WHERE test_id = :testId RETURNING user_id")
    List<Long> removeAllByTestIdAndReturnUserIds(long testId);
}
