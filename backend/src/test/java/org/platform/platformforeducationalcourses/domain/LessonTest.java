package org.platform.platformforeducationalcourses.domain;

import org.junit.jupiter.api.Test;
import org.platform.platformforeducationalcourses.domain.course.ContentType;
import org.platform.platformforeducationalcourses.domain.course.Lesson;

import static org.junit.jupiter.api.Assertions.*;

class LessonTest {

    private final ContentType DUMMY_TYPE = null;

    @Test
    void createNew_Success() {
        Lesson lesson = Lesson.createNew(1L, "Title", ContentType.VIDEO, "Content", 0, true);
        assertEquals("Title", lesson.getTitle());
        assertEquals("Content", lesson.getContent());
    }

    @Test
    void createNew_ThrowsException_WhenDataIsInvalid() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Lesson.createNew(-1L, "T", DUMMY_TYPE, "C", 0, true)),
                () -> assertThrows(IllegalArgumentException.class, () -> Lesson.createNew(1L, "", DUMMY_TYPE, "C", 0, true)),
                () -> assertThrows(IllegalArgumentException.class, () -> Lesson.createNew(1L, "T", null, "C", 0, true)),
                () -> assertThrows(IllegalArgumentException.class, () -> Lesson.createNew(1L, "T", DUMMY_TYPE, "", 0, true)),
                () -> assertThrows(IllegalArgumentException.class, () -> Lesson.createNew(1L, "T", DUMMY_TYPE, "C", -1, true))
        );
    }
}