package org.platform.platformforeducationalcourses.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.platform.platformforeducationalcourses.courseutil.ScoreCalculator;
import org.platform.platformforeducationalcourses.creator.assembler.TestAssembler;
import org.platform.platformforeducationalcourses.domain.progress.TestSubmission;
import org.platform.platformforeducationalcourses.dto.course.find.LessonFindResponse;
import org.platform.platformforeducationalcourses.dto.test.TestPostRequest;
import org.platform.platformforeducationalcourses.mapper.TestMapper;
import org.platform.platformforeducationalcourses.repository.ProgressRepository;
import org.platform.platformforeducationalcourses.repository.SubmissionsRepository;
import org.platform.platformforeducationalcourses.repository.course.TestRepository;
import org.platform.platformforeducationalcourses.service.domain.LessonService;
import org.platform.platformforeducationalcourses.service.domain.TestService;
import org.platform.platformforeducationalcourses.validator.SubmissionValidator;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseLearningServiceTest {

    @Mock
    private LessonService lessonService;
    @Mock
    private TestService testService;
    @Mock
    private SubmissionsRepository submissionsRepository;
    @Mock
    private ProgressRepository progressRepository;
    @Mock
    private TestRepository testRepository;
    @Mock
    private TestMapper testMapper;
    @Mock
    private TestAssembler testAssembler;
    @Mock
    private SubmissionValidator validator;
    @Mock
    private ScoreCalculator scoreCalculator;

    @InjectMocks
    private CourseLearningService service;

    @Test
    void getLesson_CreatesProgress_IfNotExist() {
        when(progressRepository.existsByUserIdAndLessonId(1L, 2L)).thenReturn(false);
        when(lessonService.findLesson(2L)).thenReturn(mock(LessonFindResponse.class));

        service.getLesson(1L, 2L);

        verify(progressRepository).save(any());
        verify(lessonService).findLesson(2L);
    }

    @Test
    void getLesson_DoesNotCreateProgress_IfExists() {
        when(progressRepository.existsByUserIdAndLessonId(1L, 2L)).thenReturn(true);
        when(lessonService.findLesson(2L)).thenReturn(mock(LessonFindResponse.class));

        service.getLesson(1L, 2L);

        verify(progressRepository, never()).save(any());
    }

    @Test
    void endAttempt_ThrowsException_IfAlreadyCompleted() {
        TestSubmission mockSubmission = mock(TestSubmission.class);
        when(mockSubmission.getCompletedAt()).thenReturn(LocalDateTime.now());

        when(submissionsRepository.findByUserIdAndTestId(1L, 2L)).thenReturn(Optional.of(mockSubmission));

        assertThrows(IllegalArgumentException.class, () -> service.endAttempt(mock(TestPostRequest.class), 1L, 2L));
    }

    @Test
    void getTestReview_ThrowsException_IfNotCompleted() {
        TestSubmission mockSubmission = mock(TestSubmission.class);
        when(mockSubmission.getCompletedAt()).thenReturn(null);

        when(submissionsRepository.findByUserIdAndTestId(1L, 2L)).thenReturn(Optional.of(mockSubmission));

        assertThrows(IllegalArgumentException.class, () -> service.getTestReview(1L, 2L));
    }
}