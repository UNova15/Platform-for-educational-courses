package org.platform.platformforeducationalcourses.mapper;

import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import refactor.course.domain.internal.test.AnswerOption;
import refactor.course.application.port.in.test.query.AnswerQueryResult;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.QuestionOption;

@Mapper()
public interface AnswerQuestionMapper {
    AnswerQueryResult toAnswerFindResponse(AnswerOption answerOptions);

    List<QuestionOption> toQuestionOption(Set<AnswerOption> answerOption);
}
