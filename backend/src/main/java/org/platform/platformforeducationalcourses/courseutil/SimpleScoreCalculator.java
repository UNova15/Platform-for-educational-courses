package org.platform.platformforeducationalcourses.courseutil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.platform.platformforeducationalcourses.domain.course.Question;
import org.platform.platformforeducationalcourses.dto.test.AnswerPostDto;
import org.springframework.stereotype.Component;

@Component
public class SimpleScoreCalculator implements ScoreCalculator {

    @Override
    public int calculate(List<AnswerPostDto> answers, Map<Long, Question> questionsOrderById) {
        int score = 0;
        for (AnswerPostDto answer : answers) {
            long questionId = answer.questionId();
            Question currentQuestion = questionsOrderById.get(questionId);

            Set<Long> currentOptionsIds = new HashSet<>(answer.optionIds());
            if (currentQuestion.getCorrectAnswerOptionsIds().equals(currentOptionsIds)) {
                score++;
            }
        }
        return score;
    }
}
