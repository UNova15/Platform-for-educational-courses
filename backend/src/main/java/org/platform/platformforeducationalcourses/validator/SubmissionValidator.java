package org.platform.platformforeducationalcourses.validator;

import java.util.List;
import java.util.Map;
import org.platform.platformforeducationalcourses.domain.course.AnswerOption;
import org.platform.platformforeducationalcourses.domain.course.Question;
import org.platform.platformforeducationalcourses.dto.test.AnswerPostDto;
import org.springframework.stereotype.Component;

// TODO исключения
@Component
public class SubmissionValidator {
    // проверить что в questions есть такой questionId и проверить что в нем есть такой optionId
    public void validate(List<AnswerPostDto> answers, Map<Long, Question> questions) {

        for (AnswerPostDto answer : answers) {
            Question question = questions.get(answer.questionId());

            if (question == null) {
                throw new IllegalArgumentException();
            }
            boolean isExist = question.getAnswerOptions().stream()
                    .map(AnswerOption::getId)
                    .anyMatch(id -> id == answer.optionId());
            if (!isExist) {
                throw new IllegalArgumentException();
            }
        }
    }
}
