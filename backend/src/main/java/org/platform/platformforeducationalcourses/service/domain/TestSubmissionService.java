package org.platform.platformforeducationalcourses.service.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import refactor.course.domain.test.Test;
import refactor.progress.domain.testprogress.TestAttempt;
import org.platform.platformforeducationalcourses.persistance.repository.provader.DataSubmissionsRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestSubmissionService {
    private final DataSubmissionsRepository submissionsRepository;

    public List<TestAttempt> findTestsSubmissions(long userId, List<Test> tests) {
        List<Long> testsIds = tests.stream().map(Test::getId).toList();
        return submissionsRepository.findByUserIdAndTestIdIn(userId, testsIds);
    }
}
