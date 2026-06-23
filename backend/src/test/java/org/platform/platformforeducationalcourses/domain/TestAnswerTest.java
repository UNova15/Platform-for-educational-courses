package org.platform.platformforeducationalcourses.domain;

import org.junit.jupiter.api.Test;
import org.platform.platformforeducationalcourses.domain.progress.TestAnswer;

import static org.junit.jupiter.api.Assertions.*;

class TestAnswerTest {

    @Test
    void createNew_Success() {
        TestAnswer answer = TestAnswer.createNew(100L, 200L);

        assertNotNull(answer);
        assertEquals(100L, answer.getQuestionId());
        assertEquals(200L, answer.getAnswerId());
        assertNull(answer.getTestSubmissionId());
    }

    @Test
    void createNew_ThrowsException_WhenIdsAreNegative() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> TestAnswer.createNew(-1L, 200L)),
                () -> assertThrows(IllegalArgumentException.class, () -> TestAnswer.createNew(100L, -1L))
        );
    }
}