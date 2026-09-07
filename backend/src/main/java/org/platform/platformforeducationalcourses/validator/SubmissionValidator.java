package org.platform.platformforeducationalcourses.validator;

import java.util.List;
import java.util.Map;
import refactor.course.domain.test.AnswerOption;
import refactor.course.domain.test.Question;
import org.platform.platformforeducationalcourses.dto.test.AnswerPostDto;
import org.springframework.stereotype.Component;

// TODO исключения + оптимизация
@Component
public class SubmissionValidator {
    // проверить что в questions есть такой questionId и проверить что в нем есть такой optionId
    public void validate(List<AnswerPostDto> answers, Map<Long, Question> questions) {

        for (AnswerPostDto answer : answers) {
            Question question = questions.get(answer.questionId());

            if (question == null) {
                throw new IllegalArgumentException();
            }
            boolean isExist = question.answerOptions().stream()
                    .map(AnswerOption::getId)
                    .anyMatch(id -> answer.optionIds().contains(id));
            if (!isExist) {
                throw new IllegalArgumentException();
            }
        }
    }
}
