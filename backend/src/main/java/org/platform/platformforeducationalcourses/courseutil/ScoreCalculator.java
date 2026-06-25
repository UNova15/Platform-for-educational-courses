package org.platform.platformforeducationalcourses.courseutil;

import java.util.List;
import java.util.Map;
import org.platform.platformforeducationalcourses.domain.course.Question;
import org.platform.platformforeducationalcourses.dto.test.AnswerPostDto;

public interface ScoreCalculator {
    int calculate(List<AnswerPostDto> answers, Map<Long, Question> questionsOrderById);
}
