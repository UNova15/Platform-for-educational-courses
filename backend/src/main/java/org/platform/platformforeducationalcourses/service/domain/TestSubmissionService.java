package org.platform.platformforeducationalcourses.service.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Test;
import org.platform.platformforeducationalcourses.domain.progress.TestSubmission;
import org.platform.platformforeducationalcourses.repository.SubmissionsRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestSubmissionService {
    private final SubmissionsRepository submissionsRepository;

    public List<TestSubmission> findTestsSubmissions(long userId, List<Test> tests) {
        List<Long> testsIds = tests.stream().map(Test::getId).toList();
        return submissionsRepository.findByUserIdAndTestIdIn(userId, testsIds);
    }
}
