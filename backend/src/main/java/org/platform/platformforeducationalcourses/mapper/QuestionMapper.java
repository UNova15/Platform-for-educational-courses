package org.platform.platformforeducationalcourses.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import refactor.course.domain.test.Question;
import org.platform.platformforeducationalcourses.dto.test.AnswerPostDto;
import org.platform.platformforeducationalcourses.dto.test.PostAnswerOptionRequest;
import refactor.course.application.port.in.test.query.QuestionQueryResult;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.QuestionOption;
import org.platform.platformforeducationalcourses.dto.test.studentattemptresponse.TestQuestion;

@Mapper(uses = {AnswerQuestionMapper.class})
public interface QuestionMapper {
    QuestionQueryResult toCourseQuestionFindResponse(Question question);

    AnswerPostDto toAnswerPostDto(PostAnswerOptionRequest answerOptionRequest);

    TestQuestion toTestQuestion(Question question, List<QuestionOption> options, List<Long> selectedIds);
}
