package kira.progress.adapter.out.persistence.testattempt;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface DataTestAttemptRepository extends CrudRepository<TestAttemptEntity, Long> {

    Optional<TestAttemptEntity> findByTestIdAndUserId(long testId, long userId);

    boolean existsByUserIdAndTestId(long userId, long testId);
}
