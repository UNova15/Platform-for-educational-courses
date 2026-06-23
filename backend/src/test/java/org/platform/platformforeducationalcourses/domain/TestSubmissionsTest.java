package org.platform.platformforeducationalcourses.domain;

import org.junit.jupiter.api.Test;
import org.platform.platformforeducationalcourses.courseutil.ScoreCalculator;
import org.platform.platformforeducationalcourses.domain.course.Question;
import org.platform.platformforeducationalcourses.domain.progress.TestAnswer;
import org.platform.platformforeducationalcourses.domain.progress.TestSubmission;
import org.platform.platformforeducationalcourses.dto.test.AnswerPostDto;
import org.platform.platformforeducationalcourses.validator.SubmissionValidator;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestSubmissionsTest {

    @Test
    void createNew_Success() {
        TestSubmission submission = TestSubmission.createNew(1L, 2L);

        assertEquals(1L, submission.getUserId());
        assertEquals(2L, submission.getTestId());
        assertNotNull(submission.getStartedAt());
        assertNull(submission.getCompletedAt());
        assertEquals(0, submission.getScore());
    }

    @Test
    void createNew_ThrowsException_WhenIdsAreNegative() {
        assertThrows(IllegalArgumentException.class, () -> TestSubmission.createNew(-1L, 2L));
        assertThrows(IllegalArgumentException.class, () -> TestSubmission.createNew(1L, -1L));
    }

    @Test
    void submitAnswers_Success() {
        TestSubmission submission = TestSubmission.createNew(1L, 2L);

        AnswerPostDto mockAnswerDto = mock(AnswerPostDto.class);
        when(mockAnswerDto.questionId()).thenReturn(10L);
        when(mockAnswerDto.optionId()).thenReturn(20L);

        List<AnswerPostDto> answers = List.of(mockAnswerDto);
        Map<Long, Question> mockQuestionsMap = Map.of();

        ScoreCalculator mockCalculator = mock(ScoreCalculator.class);
        when(mockCalculator.calculate(answers, mockQuestionsMap)).thenReturn(85);

        SubmissionValidator mockValidator = mock(SubmissionValidator.class);

        submission.submitAnswers(answers, mockQuestionsMap, mockCalculator, mockValidator);

        assertNotNull(submission.getCompletedAt());
        assertEquals(85, submission.getScore());
        assertEquals(1, submission.getAnswers().size());

        TestAnswer savedAnswer = submission.getAnswers().iterator().next();
        assertEquals(10L, savedAnswer.getQuestionId());
        assertEquals(20L, savedAnswer.getAnswerId());

        verify(mockValidator, times(1)).validate(answers, mockQuestionsMap);
    }

    @Test
    void submitAnswers_ThrowsException_WhenAlreadySubmitted() {
        TestSubmission submission = TestSubmission.createNew(1L, 2L);

        ScoreCalculator mockCalculator = mock(ScoreCalculator.class);
        SubmissionValidator mockValidator = mock(SubmissionValidator.class);

        submission.submitAnswers(List.of(), Map.of(), mockCalculator, mockValidator);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            submission.submitAnswers(List.of(), Map.of(), mockCalculator, mockValidator);
        });

        assertEquals("Attempt has already been saved", exception.getMessage());
    }
}