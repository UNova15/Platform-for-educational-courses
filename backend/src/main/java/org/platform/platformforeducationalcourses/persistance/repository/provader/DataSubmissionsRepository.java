package org.platform.platformforeducationalcourses.persistance.repository.provader;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.platform.platformforeducationalcourses.persistance.entity.progress.TestSubmissionEntity;
import org.springframework.data.repository.CrudRepository;

public interface DataSubmissionsRepository extends CrudRepository<TestSubmissionEntity, Long> {
    boolean existsByUserIdAndTestId(long userId, long testId);

    Optional<TestSubmissionEntity> findByUserIdAndTestId(long userId, long testId);

    List<TestSubmissionEntity> findByUserIdAndTestIdIn(long userId, Collection<Long> testIds);
}
