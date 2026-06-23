package org.platform.platformforeducationalcourses.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.domain.course.Tag;
import org.platform.platformforeducationalcourses.dto.course.CourseUpdateDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CourseTest {

    private final Tag DUMMY_TAG = null;

    @Test
    void createNew_Success() {
        Course course = Course.createNew(1L, "Java Basics", "Learn Java", DUMMY_TAG);

        assertNotNull(course);
        assertEquals(1L, course.getTeacherId());
        assertEquals("Java Basics", course.getTitle());
        assertEquals("Learn Java", course.getDescription());
        assertNotNull(course.getCreatedAt());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void createNew_ThrowsException_WhenTitleIsInvalid(String invalidTitle) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Course.createNew(1L, invalidTitle, "Desc", DUMMY_TAG);
        });

        assertEquals("Course title can not be empty", exception.getMessage());
    }

    @Test
    void updateCourse_Success() {
        Course course = Course.createNew(1L, "Old Title", "Old Desc", DUMMY_TAG);

        CourseUpdateDto dto = mock(CourseUpdateDto.class);
        when(dto.title()).thenReturn("New Title");
        when(dto.description()).thenReturn("New Desc");
        when(dto.tag()).thenReturn(DUMMY_TAG);

        course.updateCourse(dto);

        assertEquals("New Title", course.getTitle());
        assertEquals("New Desc", course.getDescription());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void updateCourse_ThrowsException_WhenTitleIsInvalid(String invalidTitle) {
        Course course = Course.createNew(1L, "Old Title", "Old Desc", DUMMY_TAG);
        CourseUpdateDto dto = mock(CourseUpdateDto.class);
        when(dto.title()).thenReturn(invalidTitle);

        assertThrows(IllegalArgumentException.class, () -> course.updateCourse(dto));
    }
}