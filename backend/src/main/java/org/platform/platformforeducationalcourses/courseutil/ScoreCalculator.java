package org.platform.platformforeducationalcourses.courseutil;

import java.util.List;
import java.util.Map;
import refactor.course.domain.test.Question;
import org.platform.platformforeducationalcourses.dto.test.AnswerPostDto;

public interface ScoreCalculator {
    int calculate(List<AnswerPostDto> answers, Map<Long, Question> questionsOrderById);
}
