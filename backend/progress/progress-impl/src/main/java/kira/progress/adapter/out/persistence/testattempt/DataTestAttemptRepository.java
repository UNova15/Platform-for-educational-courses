package kira.progress.adapter.out.persistence.testattempt;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

interface DataTestAttemptRepository extends CrudRepository<TestAttemptEntity, Long> {

    Optional<TestAttemptEntity> findByTestIdAndUserId(long testId, long userId);

    boolean existsByUserIdAndTestId(long userId, long testId);

    void removeAllByTestId(long testId);
}
