package org.platform.platformforeducationalcourses.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.platform.platformforeducationalcourses.domain.course.AnswerOption;
import org.platform.platformforeducationalcourses.domain.course.Question;
import org.springframework.test.util.ReflectionTestUtils;

class QuestionTest {

    @Test
    void createNew_Success() {
        Set<AnswerOption> options = Set.of(AnswerOption.createNew("A", true));
        Question question = Question.createNew("What is Java?", options, 1);

        assertEquals("What is Java?", question.getQuestion());
        assertEquals(1, question.getOrderIndex());
        assertFalse(question.getAnswerOptions().isEmpty());
    }

    @Test
    void createNew_ThrowsException_WhenOptionsEmptyOrNull() {
        assertThrows(IllegalArgumentException.class, () -> Question.createNew("Q", null, 1));
        assertThrows(IllegalArgumentException.class, () -> Question.createNew("Q", Set.of(), 1));
    }

    @Test
    void getCorrectAnswerOptionsId_ReturnsId_WhenCorrectOptionExists() {
        AnswerOption correctOption = AnswerOption.createNew("Right", true);
        AnswerOption wrongOption = AnswerOption.createNew("Wrong", false);

        ReflectionTestUtils.setField(correctOption, "id", 100L);
        ReflectionTestUtils.setField(wrongOption, "id", 200L);

        Question question = Question.createNew("Q", Set.of(wrongOption, correctOption), 1);

        long correctId = question.getCorrectAnswerOptionsId();
        assertEquals(100L, correctId);
    }

    @Test
    void getCorrectAnswerOptionsId_ThrowsException_WhenNoCorrectOption() {
        AnswerOption wrongOption = AnswerOption.createNew("Wrong", false);
        Question question = Question.createNew("Q", Set.of(wrongOption), 1);

        assertThrows(IllegalStateException.class, question::getCorrectAnswerOptionsId);
    }
}
