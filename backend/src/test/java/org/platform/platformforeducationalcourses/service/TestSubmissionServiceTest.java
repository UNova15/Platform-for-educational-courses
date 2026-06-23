package org.platform.platformforeducationalcourses.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.domain.progress.TestSubmission;
import org.platform.platformforeducationalcourses.repository.SubmissionsRepository;
import org.platform.platformforeducationalcourses.service.domain.TestSubmissionService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestSubmissionServiceTest {

    @Mock private SubmissionsRepository submissionsRepository;

    @InjectMocks private TestSubmissionService service;

    @org.junit.jupiter.api.Test
    void findTestsSubmissions_Success() {
        org.platform.platformforeducationalcourses.domain.course.Test test1 = mock(org.platform.platformforeducationalcourses.domain.course.Test.class);
        when(test1.getId()).thenReturn(10L);

        org.platform.platformforeducationalcourses.domain.course.Test test2 = mock(org.platform.platformforeducationalcourses.domain.course.Test.class);
        when(test2.getId()).thenReturn(20L);

        List<TestSubmission> expectedSubmissions = List.of(mock(TestSubmission.class));

        when(submissionsRepository.findByUserIdAndTestIdIn(1L, List.of(10L, 20L)))
                .thenReturn(expectedSubmissions);

        List<TestSubmission> actualSubmissions = service.findTestsSubmissions(1L, List.of(test1, test2));

        assertEquals(expectedSubmissions, actualSubmissions);
    }
}