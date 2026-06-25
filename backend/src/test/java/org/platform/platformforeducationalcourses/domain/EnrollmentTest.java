package org.platform.platformforeducationalcourses.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.platform.platformforeducationalcourses.domain.progress.Enrollment;

class EnrollmentTest {

    @Test
    void createNew_Success() {
        Enrollment enrollment = Enrollment.createNew(1L, 2L);

        assertNotNull(enrollment);
        assertEquals(1L, enrollment.getUserId());
        assertEquals(2L, enrollment.getCourseId());
        assertNotNull(enrollment.getCreatedAt());
    }

    @Test
    void createNew_ThrowsException_WhenIdsAreNegative() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Enrollment.createNew(-1L, 2L)),
                () -> assertThrows(IllegalArgumentException.class, () -> Enrollment.createNew(1L, -1L)));
    }
}
