package org.platform.platformforeducationalcourses.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;

import static org.junit.jupiter.api.Assertions.*;

class CourseModuleTest {

    @Test
    void createNew_Success() {
        CourseModule module = CourseModule.createNew(1L, "Intro", "Description", 0);

        assertEquals(1L, module.getCourseId());
        assertEquals("Intro", module.getTitle());
        assertEquals(0, module.getOrderIndex());
    }

    @Test
    void createNew_ThrowsException_WhenCourseIdOrOrderIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> CourseModule.createNew(-1L, "Title", "Desc", 0));
        assertThrows(IllegalArgumentException.class, () -> CourseModule.createNew(1L, "Title", "Desc", -1));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void createNew_ThrowsException_WhenTitleIsInvalid(String invalidTitle) {
        assertThrows(IllegalArgumentException.class, () -> CourseModule.createNew(1L, invalidTitle, "Desc", 0));
    }

    @Test
    void updateInfo_Success() {
        CourseModule module = CourseModule.createNew(1L, "Old", "Old", 0);
        module.updateInfo("New", "New", 1);

        assertEquals("New", module.getTitle());
        assertEquals("New", module.getDescription());
        assertEquals(1, module.getOrderIndex());
    }

    @Test
    void updateInfo_ThrowsException_WhenOrderIsNegative() {
        CourseModule module = CourseModule.createNew(1L, "Old", "Old", 0);
        assertThrows(IllegalArgumentException.class, () -> module.updateInfo("New", "New", -1));
    }
}