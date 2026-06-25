package org.platform.platformforeducationalcourses.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.platform.platformforeducationalcourses.domain.progress.LessonProgress;

class LessonProgressTest {

    @Test
    void createNew_Success() {
        LessonProgress progress = LessonProgress.createNew(10L, 5L);

        assertNotNull(progress);
        assertEquals(10L, progress.getUserId());
        assertEquals(5L, progress.getLessonId());
        assertNotNull(progress.getCompletedAt());
    }

    @Test
    void createNew_ThrowsException_WhenIdsAreNegative() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> LessonProgress.createNew(-1L, 5L)),
                () -> assertThrows(IllegalArgumentException.class, () -> LessonProgress.createNew(10L, -1L)));
    }
}
