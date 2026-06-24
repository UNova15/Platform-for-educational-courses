package org.platform.platformforeducationalcourses.mapper;

import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.platform.platformforeducationalcourses.domain.course.AnswerOption;
import org.platform.platformforeducationalcourses.dto.test.AnswerFindResponse;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.QuestionOption;

@Mapper()
public interface AnswerQuestionMapper {
    AnswerFindResponse toAnswerFindResponse(AnswerOption answerOptions);

    List<QuestionOption> toQuestionOption(Set<AnswerOption> answerOption);
}
