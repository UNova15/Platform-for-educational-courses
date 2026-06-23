package org.platform.platformforeducationalcourses.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.platform.platformforeducationalcourses.domain.course.AnswerOption;

import static org.junit.jupiter.api.Assertions.*;

class AnswerOptionTest {

    @Test
    void createNew_Success() {
        AnswerOption option = AnswerOption.createNew("Correct Answer", true);

        assertNotNull(option);
        assertEquals("Correct Answer", option.getOption());
        assertTrue(option.isCorrect());
        assertNull(option.getId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void createNew_ThrowsException_WhenOptionIsInvalid(String invalidOption) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AnswerOption.createNew(invalidOption, true);
        });

        assertEquals("Incorrect data to create question option", exception.getMessage());
    }
}