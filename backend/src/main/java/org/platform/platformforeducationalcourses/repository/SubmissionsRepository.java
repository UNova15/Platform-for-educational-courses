package org.platform.platformforeducationalcourses.repository;

import org.platform.platformforeducationalcourses.domain.progress.TestSubmission;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SubmissionsRepository extends CrudRepository<TestSubmission, Long> {
    boolean existsByUserIdAndTestId(long userId, long testId);

    Optional<TestSubmission> findByUserIdAndTestId(long userId, long testId);

    List<TestSubmission> findByUserIdAndTestIdIn(long userId, Collection<Long> testIds);
}
