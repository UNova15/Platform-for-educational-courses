package kira.progress.adapter.out.persistence.testattempt;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DataTestAttemptRepository extends CrudRepository<TestAttemptEntity,Long> {

    Optional<TestAttemptEntity> findByTestIdAndUserId(long testId, long userId);

    boolean existsByUserIdAndTestId(long userId, long testId);
}
